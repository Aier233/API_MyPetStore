package org.csu.mypetstore.api.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.csu.mypetstore.api.entity.User;
import org.csu.mypetstore.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Accessors(chain = true)
public class OrderVO {

    private int orderId;
    private String username;
    private Date orderDate;
    private String shipAddress1;
    private String shipAddress2;

    private String shipCity;
    private String shipState;
    private String shipZip;
    private String shipCountry;
    private String billAddress1;

    private String billAddress2;
    private String billCity;
    private String billState;
    private String billZip;
    private String billCountry;

    private String courier;
    private BigDecimal totalPrice;
    private String billToFirstName;
    private String billToLastName;
    private String shipToFirstName;

    private String shipToLastName;
    private String creditCard;
    private String expiryDate;
    private String cardType;
    private String locale;

    private String status;


    /*商户订单号，必填*/
    private String out_trade_no;
    /*订单名称，必填*/
    private String subject;
    /*付款金额，必填*/
    private StringBuffer total_amount;
    /*商品描述，可空*/
    private String body;
    /*超时时间参数*/
    private String timeout_express="10m";
    private String product_code="FAST_INSTANT_TRADE_PAY";

    private List<LineItemVO> lineItems = new ArrayList<LineItemVO>();


    public void initOrder(User account, List<CartItemVO> cart) {

        username = account.getUsername();
        orderDate = new Date(System.currentTimeMillis());

        shipToFirstName = account.getFirstname();
        shipToLastName = account.getLastname();
        shipAddress1 = account.getAddress1();
        shipAddress2 = account.getAddress2();
        shipCity = account.getCity();
        shipState = account.getState();
        shipZip = account.getZip();
        shipCountry = account.getCountry();

        billToFirstName = account.getFirstname();;
        billToLastName =account.getLastname();
        billAddress1 = account.getAddress1();
        billAddress2 = account.getAddress2();
        billCity = account.getCity();
        billState =account.getState();
        billZip = account.getZip();
        billCountry = account.getCountry();



        totalPrice = getSubTotal(cart);

        creditCard = "999 9999 9999 9999";
        expiryDate = "1/14";
        cardType = "Visa";
        courier = "UPS";
        locale = "CA";
        status = "P";

        Iterator<CartItemVO> i = cart.iterator();
        while (i.hasNext()) {
            CartItemVO cartItem = (CartItemVO) i.next();
            addLineItem(cartItem);
        }

    }


    public BigDecimal getSubTotal(List<CartItemVO> cartItemList){
        BigDecimal subTotal = new BigDecimal("0");
        Iterator<CartItemVO> items = cartItemList.iterator();
        while(items.hasNext()){
            CartItemVO cartItem = (CartItemVO) items.next();
            ItemVO item = cartItem.getItem();
            BigDecimal listPrice = item.getListPrice();
            BigDecimal quantity = new BigDecimal(String.valueOf(cartItem.getQuantity()));
            subTotal = subTotal.add(listPrice.multiply(quantity));
        }
        return subTotal;
    }

    public void addLineItem(CartItemVO cartItem) {
        LineItemVO lineItem = new LineItemVO(lineItems.size() + 1, cartItem);
        addLineItem(lineItem);
    }

    public void addLineItem(LineItemVO lineItem) {
        lineItems.add(lineItem);
    }


}
