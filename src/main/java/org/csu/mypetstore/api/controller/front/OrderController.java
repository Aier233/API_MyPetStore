package org.csu.mypetstore.api.controller.front;

import com.alipay.api.AlipayApiException;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.User;
import org.csu.mypetstore.api.service.OrderService;
import org.csu.mypetstore.api.service.PayService;
import org.csu.mypetstore.api.vo.CartItemVO;
import org.csu.mypetstore.api.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order/")
public class OrderController {

    @Resource
    private PayService payService;//调用支付服务

    @Autowired
    private OrderService orderService;

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

    @PostMapping("/confirmOrder")
    @ResponseBody
    public CommonResponse<OrderVO> confirmOrder(HttpServletRequest request){
        int orderId = orderService.getNextOrderId();
        //PrintWriter out = response.getWriter();
        String shippingAddressRequired=request.getParameter("shippingAddressRequired");
        //没选修改地址
        if(shippingAddressRequired.equals("false")){
            String cardType = request.getParameter("cardType");
            String creditCard = request.getParameter("creditCard");
            String expiryDate = request.getParameter("expiryDate");
//            String shipToFirstName = request.getParameter("shipToFirstName");
//            String shipToLastName = request.getParameter("shipToLastName");
//            String shipAddress1 = request.getParameter("shipAddress1");
//            String shipAddress2 = request.getParameter("shipAddress2");
//            String shipCity = request.getParameter("shipCity");
//            String shipState = request.getParameter("shipState");
//            String shipZip = request.getParameter("shipZip");
//            String shipCountry = request.getParameter("shipCountry");
            HttpSession session = request.getSession();
            OrderVO order = (OrderVO)session.getAttribute("order");
            order.setCardType(cardType);
            order.setCreditCard(creditCard);
            order.setExpiryDate(expiryDate);
            order.setOrderId(orderId);
//            order.setShipToFirstName(shipToFirstName);
//            order.setShipToLastName(shipToLastName);
//            order.setShipAddress1(shipAddress1);
//            order.setShipAddress2(shipAddress2);
//            order.setShipCity(shipCity);
//            order.setShipState(shipState);
//            order.setShipZip(shipZip);
//            order.setShipCountry(shipCountry);
            //覆盖原来的order
            session.setAttribute("order",order);
            //out.print("Checked");
            return CommonResponse.createForSuccess(order);

        }else{
            HttpSession session = request.getSession();
            String cardType = request.getParameter("cardType");
            String creditCard = request.getParameter("creditCard");
            String expiryDate = request.getParameter("expiryDate");
            User account = (User)session.getAttribute("login_account");

            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String address1 = request.getParameter("address1");
            String address2 = request.getParameter("address2");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String zip = request.getParameter("zip");
            String country = request.getParameter("country");
            // 修改订单消息
            OrderVO order = (OrderVO)session.getAttribute("order");
            order.setOrderId(orderId);
            order.setCardType(cardType);
            order.setCreditCard(creditCard);
            order.setExpiryDate(expiryDate);

            order.setShipToFirstName(firstName);
            order.setShipToLastName(lastName);
            order.setBillToFirstName(account.getFirstname());
            order.setBillToLastName(account.getLastname());

            order.setShipAddress1(address1);
            order.setShipAddress2(address2);
            order.setBillAddress1(account.getAddress1());
            order.setBillAddress2(account.getAddress2());


            order.setShipCity(city);
            order.setBillCity(account.getCity());


            order.setShipState(state);
            order.setBillState(account.getState());


            order.setShipZip(zip);
            order.setBillZip(account.getZip());


            order.setShipCountry(country);
            order.setBillCountry(account.getCountry());


            session.setAttribute("order",order);
            //out.print("No Checked");
            return CommonResponse.createForSuccess(order);
        }
        //out.flush();
        //out.close();
    }

    @GetMapping("getConfirmOrder")
    @ResponseBody
    public CommonResponse<OrderVO>  getConfirmOrder(HttpSession session){
        OrderVO ordervo = (OrderVO)session.getAttribute("order");
        return CommonResponse.createForSuccess(ordervo);
    }


    @PostMapping("/finalOrder")
    @ResponseBody
    public String order(HttpServletRequest request) throws AlipayApiException {
        HttpSession session = request.getSession();
        OrderVO order = (OrderVO) session.getAttribute("order");

        orderService.InsertOrderVOToDB(order);

        return  payService.aliPay(new OrderVO()
                .setBody(order.getUsername())
                .setOut_trade_no(order.getLineItems().get(0).getItemId())
                .setTotal_amount(new StringBuffer().append(order.getTotalPrice()))
                .setSubject("MyPetStore OrderId: "+order.getOrderId()));
    }

}
