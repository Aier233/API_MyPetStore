package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {

    @TableField(value ="id")
    private String id;

    @TableId(value = "username",type = IdType.INPUT)
    private String username;

    @TableField(value ="password")
    private String password;

    @TableField(value ="firstname")
    private String firstname;

    @TableField(value ="lastname")
    private String lastname;

    @TableField(value ="email")
    private String email;

    @TableField(value ="phone")
    private String phone;

    @TableField(value ="address1")
    private String address1;

    @TableField(value ="address2")
    private String address2;

    @TableField(value ="city")
    private String city;

    @TableField(value ="state")
    private String state;

    @TableField(value ="zip")
    private String zip;

    @TableField(value ="country")
    private String country;

    @TableField(value ="languagepre")
    private String languagepre;

    @TableField(value ="favoritecata")
    private String favoritecata;

    @TableField(value ="iflist")
    private String iflist;

    @TableField(value ="ifbanner")
    private String ifbanner;
}
