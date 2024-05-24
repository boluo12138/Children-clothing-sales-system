package com.wzu.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能： 公告信息表
 * 作者：菠萝
 * 日期：2024/3/31 14:32
 */
@Data
@TableName("x_notice")
public class Notice  implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
    // 标题
    private String title;
    // 内容
    private String content;
    // 创建时间
    private String time;
    // 创建人
    private Integer userId;
    @TableField(exist = false)
    private String userName;
}
