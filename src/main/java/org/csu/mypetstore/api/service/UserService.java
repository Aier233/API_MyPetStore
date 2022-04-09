package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.User;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

public interface UserService {


    CommonResponse<User> getAccountByUsernameAndPassword(String username,String password);
    User findUserByUsername(String username);
    CommonResponse<User> getLoginAccountInfo(HttpSession session);
}
