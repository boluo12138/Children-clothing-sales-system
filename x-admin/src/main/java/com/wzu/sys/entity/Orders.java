package com.wzu.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：订单信息表
 * 作者：菠萝
 * 日期：2024/4/5 23:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("x_orders")
public class Orders extends PagePara implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer businessId;
    private Integer goodsId;
    private String orderId;
    private Integer addressId;
    private Integer num;
    private Double price;
    private String status;

    @TableField(exist = false)
    private List<Cart> cartData;

    @TableField(exist = false)
    private String businessName;
    @TableField(exist = false)
    private String goodsName;
    @TableField(exist = false)
    private String goodsImg;
    @TableField(exist = false)
    private String goodsUnit;
    @TableField(exist = false)
    private Double goodsPrice;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String useraddress;
    @TableField(exist = false)
    private String phone;
}
