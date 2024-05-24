package com.wzu.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能：地址信息表
 * 作者：菠萝
 * 日期：2024/4/12 9:40
 */
@Data
@TableName("x_address")
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String username;
    private String useraddress;
    private String phone;
}
