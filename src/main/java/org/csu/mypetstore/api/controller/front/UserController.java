package org.csu.mypetstore.api.controller.front;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.User;
import org.csu.mypetstore.api.service.UserService;
import org.csu.mypetstore.api.util.AuthCodeUtil;
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

@Controller
@RequestMapping("/account/")
public class UserController {

    @Autowired
    private UserService userService;

    //登入
    @PostMapping("login")
    @ResponseBody
    public CommonResponse<User> login(@RequestParam String username, @RequestParam String password, HttpSession session){
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

    //验证码
    //todo:将值显示到前端
    @GetMapping("authCode")
    public String authCode(HttpServletRequest request, HttpServletResponse response,Integer number) throws IOException {
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


        request.getSession().setAttribute("authCode",authCode);
        response.setContentType("image/jpeg");
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-control","no-cache");
        response.setIntHeader("Expires",-1);
        g.dispose();
        ImageIO.write(image,"JPEG",response.getOutputStream());
        return null;
    }
}

