package org.csu.mypetstore.api.controller.front;


import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.persistence.CartMapper;
import org.csu.mypetstore.api.service.CartService;
import org.csu.mypetstore.api.vo.CartItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("getCart")
    @ResponseBody
    public CommonResponse<List<CartItemVO>> selectItemByUsername(String username, HttpSession session){

        CommonResponse<List<CartItemVO>> listCartItemVOResponse = cartService.selectItemByUsername(username,session);
        session.setAttribute("cart",listCartItemVOResponse.getData());
        return listCartItemVOResponse;
    }

}
