package org.csu.mypetstore.api.service.imp;


import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.User;
import org.csu.mypetstore.api.persistence.UserMapper;
import org.csu.mypetstore.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service("accountService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public CommonResponse<User> getAccountByUsernameAndPassword(String username,String password) {
        User user = userMapper.selectById(username);
        if(user==null){
            return CommonResponse.createForSuccessMessage("没有该username的user");
        }
        else{
            if(!user.getPassword().equals(password)){
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
}
