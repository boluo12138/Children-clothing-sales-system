package com.wzu.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能：购物车信息表
 * 作者：菠萝
 * 日期：2024/4/7 17:54
 */
@Data
@TableName("x_cart")
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    /** ID */
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer businessId;
    private Integer goodsId;
    private Integer num;



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
    private String activityDiscount;
    @TableField(exist = false)
    private Double activityPrice;
    @TableField(exist = false)
    private String activityStatus;
}
