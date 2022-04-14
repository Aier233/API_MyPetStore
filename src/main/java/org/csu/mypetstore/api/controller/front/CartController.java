package org.csu.mypetstore.api.controller.front;


import com.zhenzi.sms.ZhenziSmsClient;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.entity.Item;
import org.csu.mypetstore.api.entity.User;
import org.csu.mypetstore.api.persistence.CartMapper;
import org.csu.mypetstore.api.service.CartService;
import org.csu.mypetstore.api.service.CatalogService;
import org.csu.mypetstore.api.service.UserService;
import org.csu.mypetstore.api.util.RandomNumberUtil;
import org.csu.mypetstore.api.vo.CartItemVO;
import org.csu.mypetstore.api.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private UserService userService;

    @GetMapping("getCart")
    @ResponseBody
    public CommonResponse<List<CartItemVO>> selectItemByUsername(String username, HttpSession session){

        CommonResponse<List<CartItemVO>> listCartItemVOResponse = cartService.selectItemByUsername(username,session);
        session.setAttribute("cart",listCartItemVOResponse.getData());
//        System.out.println("........resp"+listCartItemVOResponse.getStatus());
        return listCartItemVOResponse;
    }

    @PostMapping("updateCartItem")
    @ResponseBody
    public void updateCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String itemId = request.getParameter("itemId");
        String quantityStr = request.getParameter("quantity");
        int quantity = 0;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if(quantityStr == "" || quantityStr.equals(null)){
            cartService.removeCartItemByUsernameAndItemId(username, itemId);
            out.write("{\"isRemoved\":\""+true+"{\"itemId\":\"" + itemId + "\"}");
        }
        else {
            quantity = Integer.parseInt(quantityStr);
            System.out.println(quantity);

            if(quantity == 0){
                cartService.removeCartItemByUsernameAndItemId(username, itemId);
                out.write("{\"isRemoved\":\"" + true + "\",\"itemId\":\"" + itemId + "\"}");
            }
            else {
                cartService.updateItemByItemIdAndQuantity(username, itemId, quantity);
                CartItemVO item = cartService.getCartItemByUsernameAndItemId(username, itemId);
                String html = "<fmt:formatNumber type='number' pattern='$#,##0.00'>$" + item.getTotal() + "</fmt:formatNumber>";
//                System.out.println("html"+html);
                out.write("{\"isRemoved\":\"" + false + "\",\"itemId\":\"" + itemId + "\",\"quantity\":\"" + quantity +
                        "\",\"totalcost\":\"" + item.getTotal() + "\",\"html\":\"" + html + "\"}");
            }
        }

        CommonResponse<List<CartItemVO>> listCartItemVOResponse = selectItemByUsername(username,session);

//        session.setAttribute("login_account",userService.findUserByUsername(username));
//        session.setAttribute("cart", listCartItemVOResponse.getData());
//        System.out.println("........resp"+listCartItemVOResponse.getStatus());

        out.flush();
        out.close();

    }

    @GetMapping("removeItemFromCart")
    @ResponseBody
    public void removeItemFromCart(String username,String itemId,HttpSession session){

        CartItemVO cartItemVO = cartService.getCartItemByUsernameAndItemId(username,itemId);
        if(cartItemVO != null){
            cartService.removeCartItemByUsernameAndItemId(username,itemId);
        }

        CommonResponse<List<CartItemVO> > cart = cartService.selectItemByUsername(username,session);
//        session.setAttribute("login_account",userService.findUserByUsername(username));
//        session.setAttribute("cart", cart.getData());

    }

    @GetMapping("/addItemToCart")
    @ResponseBody
    public CommonResponse addItemToCart(String workingItemId,HttpSession session){


        User loginAccount = (User) session.getAttribute("login_account");
        String username = loginAccount.getUsername();
        CartItemVO cartItemVO = cartService.getCartItemByUsernameAndItemId(username, workingItemId);

        if (cartItemVO != null) {
            if(!cartItemVO.isPay()) {
                cartService.incrementItemByUsernameAndItemId(username, workingItemId);
            }
            else {
                cartService.updateItemByItemIdAndPay(username, workingItemId, false);
                cartService.updateItemByItemIdAndQuantity(username, workingItemId, 1);
            }
        } else {
            boolean isInStock = catalogService.isItemInStock(workingItemId);
            ItemVO itemVO = catalogService.getItem(workingItemId);
            cartService.addItemByUsernameAndItemId(username, itemVO, isInStock);
        }

        CommonResponse<List<CartItemVO> > cart = cartService.selectItemByUsername(username,session);

        return CommonResponse.createForSuccess();
    }

    @PostMapping("/phoneVCode")
    @ResponseBody
    public void phoneCode(HttpServletRequest request,String phoneNumber){

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
        }catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error","验证码发送失败");
        }
    }


}
