package org.csu.mypetstore.api.service.imp;


import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.User;
import org.csu.mypetstore.api.persistence.UserMapper;
import org.csu.mypetstore.api.service.UserService;
import org.csu.mypetstore.api.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service("accountService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    //MD5 加密所用的盐
    private static final String salt="1a2b3c4d";

    @Override
    public CommonResponse<User> getAccountByUsernameAndPassword(String username,String password) {
        User user = userMapper.selectById(username);

        String MD5Password = MD5Util.inputPassToDBPass(password,salt);
        System.out.println("*******************************************");
        System.out.println("输入密码： "+password+"  MD5加密后： "+MD5Password);
        System.out.println("*******************************************");
        if(user==null){
            return CommonResponse.createForSuccessMessage("没有该username的user");
        }
        else{
            if(!user.getPassword().equals(MD5Password)){
                return CommonResponse.createForSuccessMessage("password不正确");
            }
            else{
                return CommonResponse.createForSuccess(user);
            }
        }
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userMapper.selectById(username);
        return user;
    }


    @Override
    public CommonResponse<User> getLoginAccountInfo(HttpSession session) {
        return null;
    }

    @Override
    public CommonResponse<User> updateUser(User user) {
        if(user.getUsername()==null||user.getPassword()==null){
            return CommonResponse.createForSuccessMessage("用户名或密码不能为空");
        }else{
            userMapper.updateById(user);//根据id选择修改的user
            return CommonResponse.createForSuccess(user);
        }
    }

    @Override
    public CommonResponse<User> insertUser(User user) {
        if(user.getUsername()==null||user.getPassword()==null){
            return CommonResponse.createForSuccessMessage("用户名或密码不能为空");
        }else{
            userMapper.insert(user);
            return CommonResponse.createForSuccess(user);//用户插入成功
        }
    }
}


