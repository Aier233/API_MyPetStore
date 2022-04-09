package org.csu.mypetstore.api.service.imp;

import org.csu.mypetstore.api.entity.Order;
import org.csu.mypetstore.api.entity.OrderStatus;
import org.csu.mypetstore.api.entity.Sequence;
import org.csu.mypetstore.api.persistence.OrderMapper;
import org.csu.mypetstore.api.persistence.OrderStatusMapper;
import org.csu.mypetstore.api.persistence.SequenceMapper;
import org.csu.mypetstore.api.service.OrderService;
import org.csu.mypetstore.api.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SequenceMapper sequenceMapper;


    public OrderVO getOrderVO(String orderId){

        OrderStatus orderStatus =orderStatusMapper.selectById(orderId);
        Order order = orderMapper.selectById(orderId);
        OrderVO orderVO = orderToOrderVO(order,orderStatus);
        return orderVO;
    }

    private OrderVO orderToOrderVO(Order order, OrderStatus orderStatus){

        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(order.getOrderId());
        orderVO.setUsername(order.getUsername());
        orderVO.setOrderDate(order.getOrderDate());
        orderVO.setShipAddress1(order.getShipAddress1());
        orderVO.setShipAddress2(order.getShipAddress2());
        orderVO.setShipCity(order.getShipCity());
        orderVO.setShipState(order.getShipState());
        orderVO.setShipZip(order.getShipZip());
        orderVO.setShipCountry(order.getShipCountry());
        orderVO.setBillAddress1(order.getBillAddress1());
        orderVO.setBillAddress2(order.getBillAddress2());
        orderVO.setBillCity(order.getBillCity());
        orderVO.setBillState(order.getBillState());
        orderVO.setBillZip(order.getBillZip());
        orderVO.setBillCountry(order.getBillCountry());
        orderVO.setCourier(order.getCourier());
        orderVO.setTotalPrice(order.getTotalPrice());
        orderVO.setBillToFirstName(order.getBillToFirstName());
        orderVO.setBillToLastName(order.getBillToLastName());
        orderVO.setShipToFirstName(order.getShipToFirstName());
        orderVO.setShipToLastName(order.getShipToLastName());
        orderVO.setCreditCard(order.getCreditCard());
        orderVO.setExpiryDate(order.getExpiryDate());
        orderVO.setCardType(order.getCardType());
        orderVO.setLocale(order.getLocale());


        orderVO.setStatus(orderStatus.getStatus());
        return orderVO;
    }


    public int getNextOrderId(){
        Sequence ordernum = sequenceMapper.selectById("ordernum");
        return ordernum.getNextId()+1;
    }

}
