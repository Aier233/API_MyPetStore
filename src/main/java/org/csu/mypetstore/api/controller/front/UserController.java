package org.csu.mypetstore.api.controller.front;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.User;
import org.csu.mypetstore.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

}
