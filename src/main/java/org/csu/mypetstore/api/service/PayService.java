package org.csu.mypetstore.api.service;

import com.alipay.api.AlipayApiException;
import org.csu.mypetstore.api.vo.OrderVO;

public interface PayService {
    /*支付宝*/
    String aliPay(OrderVO orderVo) throws AlipayApiException;
}
