package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description与数据库表映射
 * @Date 2022/3/27 3:35 下午
 * @Author RessMatthew
 * @Version 1.0
 **/

@Data
@TableName("inventory")
public class ItemInventory {
    @TableId("itemid")
    private String itemId;
    @TableField("qty")
    private int quantity;
}
