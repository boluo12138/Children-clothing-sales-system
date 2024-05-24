package com.wzu.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 * 作者：菠萝
 * 日期：2024/4/13 15:39
 */
@Data
@TableName("x_type")

public class Type implements Serializable {
    @TableId(type = IdType.AUTO, value = "id")
    private Integer id;
    private String name;
    private String description;
    private Integer parentId;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Type> children;

}
