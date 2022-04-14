package org.csu.mypetstore.api.controller.front;

import com.zhenzi.sms.ZhenziSmsClient;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.User;
import org.csu.mypetstore.api.service.UserService;
import org.csu.mypetstore.api.util.AuthCodeUtil;
import org.csu.mypetstore.api.util.RandomNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/account/")
public class UserController {

    @Autowired
    private UserService userService;

    //登入
    @PostMapping("login")
    @ResponseBody
    public CommonResponse<User> login(@RequestParam String username, @RequestParam String password,HttpSession session){

        String msg = null;
        CommonResponse<User> response = userService.getAccountByUsernameAndPassword(username,password);
        if(response.isSuccess()){
            session.setAttribute("login_account",response.getData());
        }
        return response;
    }

    //获得登入用户的用户信息
    @PostMapping("get_login_account_info")
    @ResponseBody
    public CommonResponse<User> getLoginAccountInfo(HttpSession session){
        User loginAccount = (User) session.getAttribute("login_account");
        if(loginAccount !=null){
            return CommonResponse.createForSuccess(loginAccount);
        }
        else{
            return CommonResponse.createForSuccessMessage("没有用户登录");
        }
    }

    //判断用户是否存在
    @GetMapping("/usernameIsExist")
    public void usernameIsExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        User user = userService.findUserByUsername(username);
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        if(username==""){
            out.print("Empty");
        }
        else if(user != null){
            out.print("Exist");
        }
        else {
            out.print("Not Exist");
        }
        out.flush();
        out.close();
    }

    //退出
    @GetMapping("signout")
    @ResponseBody
    public CommonResponse<User> signout(HttpServletRequest request,HttpServletResponse response)throws IOException {
        if(request.getSession().getAttribute("login_account") != null) {
            request.getSession().removeAttribute("login_account");
//            System.out.println("成功登出");
            return CommonResponse.createForSuccessMessage("成功登出");
        }else{
//            System.out.println("用户不存在，无法登出");
            return CommonResponse.createForError("当前用户不存在");
        }
    }

    @PostMapping("register")
    @ResponseBody
    public CommonResponse<User> register(HttpServletRequest request,User user){

        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String firstname=request.getParameter("firstname");
        String lastname=request.getParameter("lastname");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String address1=request.getParameter("address1");
        String address2=request.getParameter("address2");
        String city=request.getParameter("city");
        String state=request.getParameter("state");
        String zip=request.getParameter("zip");
        String country=request.getParameter("country");
        String languagepre=request.getParameter("languagepre");
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress1(address1);
        user.setAddress2(address2);
        user.setCity(city);
        user.setState(state);
        user.setZip(zip);
        user.setCountry(country);
        user.setLanguagepre(languagepre);

        CommonResponse<User> response=userService.insertUser(user);

        return response;
    }

    //修改用户信息
    @PostMapping("editAccount")
    @ResponseBody
    public CommonResponse<User> saveAccount(HttpServletRequest request,HttpSession session){
        User user = (User) session.getAttribute("login_account");//当前登录的用户信息

        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String firstname=request.getParameter("firstname");
        String lastname=request.getParameter("lastname");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String address1=request.getParameter("address1");
        String address2=request.getParameter("address2");
        String city=request.getParameter("city");
        String state=request.getParameter("state");
        String zip=request.getParameter("zip");
        String country=request.getParameter("country");
        String languagepre=request.getParameter("languagepre");
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress1(address1);
        user.setAddress2(address2);
        user.setCity(city);
        user.setState(state);
        user.setZip(zip);
        user.setCountry(country);
        user.setLanguagepre(languagepre);

        CommonResponse<User> response=userService.updateUser(user);
        return response;
    }

    //图片验证码
    //todo:将值显示到前端
    @GetMapping("authCode")
    public void authCode(HttpSession session, HttpServletResponse response,Integer number) throws IOException {
        AuthCodeUtil authCodeUtil=new AuthCodeUtil();
        BufferedImage image = new BufferedImage(authCodeUtil.WIDTH,authCodeUtil.HEIGHT,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        String authCode = "";
        authCodeUtil.setBackground(g);
        authCodeUtil.setBorder(g);
        authCodeUtil.setRandomLine(g);
        authCode = authCodeUtil.setWriteDate(g);
        System.out.println("*******************************************");
        System.out.println("当前验证码："+authCode);
        System.out.println("*******************************************");


        session.setAttribute("authCode",authCode);
        response.setContentType("image/jpeg");
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-control","no-cache");
        response.setIntHeader("Expires",-1);
        g.dispose();
        ImageIO.write(image,"JPEG",response.getOutputStream());

    }

    //获取图片验证码
    @GetMapping("getAuthCode")
    @ResponseBody
    public CommonResponse<User> getAuthCode(HttpSession session){
        String authCode = (String)session.getAttribute("authCode");
//        System.out.println(authCode);
        if (authCode==null)return CommonResponse.createForError("验证码未创建");
        else return CommonResponse.createForSuccessMessage(authCode);
    }

    //生成并发送手机验证码（手机登录）
    @PostMapping("phoneVCode")
    @ResponseBody
    public CommonResponse phoneCode(HttpServletRequest request,String phoneNumber){

//        System.out.println("1");
//        System.out.println(phoneNumber);

        String apiUrl = "https://sms_developer.zhenzikj.com";
        String appId  = "111103";
        String appSecret = "761719c1-e3cc-41dc-9074-01744465caad";
        String reminder = null;
        String vCode = null;

        try{
            vCode = RandomNumberUtil.getRandomNumber();

            ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("number", phoneNumber);
            params.put("templateId", "8485");
            String[] templateParams = new String[1];
            templateParams[0] = vCode;
            System.out.println(vCode);
            params.put("templateParams", templateParams);
            String result = client.send(params);

            reminder = "验证码发送成功";
            request.setAttribute("reminder",reminder);
            request.getSession().setAttribute("vCode",vCode);

            System.out.println(result);

            return CommonResponse.createForSuccessMessage("验证码已成功发送，请注意查收！");

        }catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error","验证码发送失败");
            return CommonResponse.createForError("出了点问题，请刷新页面后重试！");
        }
    }

    //手机号登陆
    @PostMapping("signinPhone")
    @ResponseBody
    public CommonResponse signinPhone(HttpSession session,String phoneNumber,String username,String inputVCode){
        String phoneVCode = (String)session.getAttribute("vCode");
        if(phoneVCode==null)return CommonResponse.createForSuccessMessage("验证码未创建");
        else {

            User user = userService.findUserByUsername(username);
            if(user==null){
                return CommonResponse.createForSuccessMessage("查无此人");
            }else if (!phoneNumber.equals(user.getPhone())){
                return CommonResponse.createForSuccessMessage("用户名与手机号不匹配");
            }else if(!inputVCode.equals(phoneVCode)){
                return CommonResponse.createForSuccessMessage("手机验证码有误，请重新输入！");
            }else {
                session.setAttribute("login_account",user);
                return CommonResponse.createForSuccessMessage("登录成功");
            }

        }


    }




}

