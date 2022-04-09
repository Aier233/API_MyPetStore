package org.csu.mypetstore.api.vo;

import org.csu.mypetstore.api.entity.Item;

import java.math.BigDecimal;

public class CartItemVO {

    private String username;
    private ItemVO itemVO;
    private boolean instock;
    private int quantity;
    private BigDecimal totalCost;
    private boolean pay;
    public BigDecimal getTotal() {
        return totalCost;
    }

    public void setTotal(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public ItemVO getItem() {
        return itemVO;
    }

    public void setItem(ItemVO itemVO) {
        this.itemVO = itemVO;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotal();
    }

    public boolean isInStock() {
        return instock;
    }

    public void setInStock(boolean inStock) {
        this.instock = inStock;
    }

    private void calculateTotal(){
        if(itemVO != null && itemVO.getListPrice() != null){
            totalCost = itemVO.getListPrice().multiply(new BigDecimal(quantity));
        }
        else{
            totalCost = null;
        }
    }

    public void incrementQuantity() {
        quantity++;
        calculateTotal();
    }

    public void updateQuantity(int quantity){
        this.quantity = quantity;
        calculateTotal();
    }

    public boolean isPay() {
        return pay;
    }

    public void setPay(boolean pay) {
        this.pay = pay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
