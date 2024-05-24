package com.wzu.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能：收藏信息表
 * 作者：菠萝
 * 日期：2024/4/13 10:00
 */
@Data
@TableName("x_collect")
public class Collect implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer businessId;
    private Integer goodsId;

    @TableField(exist = false)
    private String businessName;
    @TableField(exist = false)
    private String goodsName;
    @TableField(exist = false)
    private String goodsImg;
    @TableField(exist = false)
    private String goodUnit;
    @TableField(exist = false)
    private Double goodsPrice;

}
