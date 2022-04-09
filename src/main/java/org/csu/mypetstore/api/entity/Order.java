package org.csu.mypetstore.api.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@TableName("orders")
public class Order {

    @TableId(value = "orderId",type = IdType.INPUT)
    private int orderId;
    @TableField(value ="userid" )
    private String username;
    @TableField(value ="orderdate" )
    private Date orderDate;
    @TableField(value ="shipaddr1" )
    private String shipAddress1;
    @TableField(value ="shipaddr2" )
    private String shipAddress2;


    @TableField(value ="shipcity" )
    private String shipCity;
    @TableField(value ="shipstate" )
    private String shipState;
    @TableField(value ="shipzip" )
    private String shipZip;
    @TableField(value ="shipcountry" )
    private String shipCountry;
    @TableField(value ="billaddr1" )
    private String billAddress1;


    @TableField(value ="billcity" )
    private String billAddress2;
    @TableField(value ="billstate" )
    private String billCity;
    @TableField(value ="billzip" )
    private String billState;
    @TableField(value ="billcountry" )
    private String billZip;
    @TableField(value ="courier" )
    private String billCountry;


    @TableField(value ="totalprice" )
    private String courier;
    @TableField(value ="billtofirstname" )
    private BigDecimal totalPrice;
    @TableField(value ="billtolastname" )
    private String billToFirstName;
    @TableField(value ="orderdate" )
    private String billToLastName;
    @TableField(value ="orderdate" )
    private String shipToFirstName;

    @TableField(value ="orderdate" )
    private String shipToLastName;
    @TableField(value ="orderdate" )
    private String creditCard;
    @TableField(value ="orderdate" )
    private String expiryDate;
    @TableField(value ="orderdate" )
    private String cardType;
    @TableField(value ="orderdate" )
    private String locale;


    @TableField(value ="orderdate" )
    private String status;

}
