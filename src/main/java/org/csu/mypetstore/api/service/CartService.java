package org.csu.mypetstore.api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.vo.CartItemVO;
import org.csu.mypetstore.api.vo.ItemVO;

import javax.servlet.http.HttpSession;


public interface CartService {
    void addItemByUsernameAndItemId(String username, ItemVO item,boolean isInStock);

    void incrementItemByUsernameAndItemId(String username,String itemId);

    CartItemVO getCartItemByUsernameAndItemId(String username, String itemId);

    void removeCartItemByUsernameAndItemId(String username, String itemId);

    void updateItemByItemIdAndQuantity(String username, String itemId, int quantity);

    CommonResponse<List<CartItemVO>> selectItemByUsername(String username,HttpSession session );

    void updateItemByItemIdAndPay(String username, String itemId, boolean pay);

    void updateCartToPay(HttpSession session);

    void addItem(String username, BigDecimal listPrice, String itemId);
}
