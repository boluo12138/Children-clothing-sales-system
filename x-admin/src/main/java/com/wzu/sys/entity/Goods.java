package com.wzu.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能：商品信息表
 * 作者：菠萝
 * 日期：2024/4/2 13:05
 */
@Data
@TableName("x_Goods")
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
    /** 商品名称 */
    private String name;
    /** 商品描述 */
    private String description;
    /** 商品主图 */
    private String img;
    private Double price;
    private String unit;
    private Integer count;
    @TableField(value="type_id")
    private Integer typeId;
    @TableField(value="business_id")
    private Integer businessId;
    @TableField(value="activity_id")
    private Integer activityId;

    @TableField(exist = false)
    private String typeName;
    @TableField(exist = false)
    private String businessName;
    @TableField(exist = false)
    private String activityDiscount;
    @TableField(exist = false)
    private Double activityPrice;
    @TableField(exist = false)
    private String activityName;
    @TableField(exist = false)
    private String activityDescription;
    @TableField(exist = false)
    private String activityStatus;



}
