package com.wzu.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能：评论信息表
 * 作者：菠萝
 * 日期：2024/4/6 14:19
 */
@Data
@TableName("x_comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer businessId;
    private Integer goodsId;
    private String time;
    private String content;

    @TableField(exist = false)
    private String businessName;
    @TableField(exist = false)
    private String goodsName;
    @TableField(exist = false)
    private String userAvatar;
    @TableField(exist = false)
    private String userName;

}
