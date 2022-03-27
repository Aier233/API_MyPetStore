package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description
 * @Date 2022/3/27 11:59 上午
 * @Author RessMatthew
 * @Version 1.0
 **/

@Data
@TableName("category")
public class Category {

    @TableId(value = "catid",type = IdType.INPUT)
    private String categoryId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "descn")
    private String description;


}
