package org.csu.mypetstore.api.service;


import org.csu.mypetstore.api.vo.OrderVO;

public interface OrderService {





    OrderVO getOrderVO(String orderId);
    int getNextOrderId();

}
