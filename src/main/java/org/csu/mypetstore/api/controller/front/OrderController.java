package org.csu.mypetstore.api.controller.front;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.User;
import org.csu.mypetstore.api.vo.CartItemVO;
import org.csu.mypetstore.api.vo.OrderVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order/")
public class OrderController {

    @GetMapping("getOrder")
    @ResponseBody
    public CommonResponse<OrderVO>  getOrder(HttpSession session){
        List<CartItemVO> cart = (List<CartItemVO>) session.getAttribute("cart");
        User account = (User)session.getAttribute("login_account");

        OrderVO ordervo = new OrderVO();
        ordervo.initOrder(account,cart);
        session.setAttribute("order",ordervo);
        return CommonResponse.createForSuccess(ordervo);
    }
}
