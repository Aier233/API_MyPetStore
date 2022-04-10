package org.csu.mypetstore.api.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Cart;
import org.csu.mypetstore.api.entity.User;
import org.csu.mypetstore.api.persistence.CartMapper;
import org.csu.mypetstore.api.service.CartService;
import org.csu.mypetstore.api.service.CatalogService;
import org.csu.mypetstore.api.vo.CartItemVO;
import org.csu.mypetstore.api.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CatalogService catalogService;


    //重构
    //通过用户名获得没有支付的购物车项目
    @Override
    public CommonResponse<List<CartItemVO>> selectItemByUsername(String username,HttpSession session) {


        User loginAccount = (User) session.getAttribute("login_account");
        if(loginAccount==null){
            return CommonResponse.createForNeedLogin("请先登录后再查看购物车");
        }

        List<CartItemVO> cartItemList=new ArrayList<>();

        QueryWrapper<Cart> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);


        if(cartList.isEmpty()){
            return CommonResponse.createForSuccessMessage("没有分类信息");
        }

        Iterator cartListIterato=cartList.iterator();
        for(int i=0;cartListIterato.hasNext()&&i<cartList.size();i++) {
            CartItemVO result = new CartItemVO();
            String usernameTemp =cartList.get(i).getUsername();
            String itemId = cartList.get(i).getItemId();
            boolean isInStock = cartList.get(i).isInstock();
            int quantity = cartList.get(i).getQuantity();
            BigDecimal totalCost = cartList.get(i).getTotalCost();
            boolean pay = cartList.get(i).getPay();
            if(!pay) {
                result.setItem(catalogService.getItem(itemId));
                result.setUsername(usernameTemp);
                result.setInStock(isInStock);
                result.setQuantity(quantity);
                result.setTotal(totalCost);
                cartItemList.add(result);
            }
        }
        if(cartItemList.size()==0){
            return CommonResponse.createForSuccessMessage("购物车为空");
        }
        return CommonResponse.createForSuccess(cartItemList);
    }






    //未重构部分
    @Override
    public void addItemByUsernameAndItemId(String username, ItemVO item, boolean isInStock) {

    }

    @Override
    public void incrementItemByUsernameAndItemId(String username, String itemId) {

    }

    @Override
    public CartItemVO getCartItemByUsernameAndItemId(String username, String itemId) {
        return null;
    }

    @Override
    public void removeCartItemByUsernameAndItemId(String username, String itemId) {

    }

    @Override
    public void updateItemByItemIdAndQuantity(String username, String itemId, int quantity) {

    }

    @Override
    public void updateItemByItemIdAndPay(String username, String itemId, boolean pay) {

    }
}
