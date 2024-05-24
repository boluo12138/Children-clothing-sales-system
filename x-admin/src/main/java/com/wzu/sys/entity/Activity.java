package com.wzu.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 功能：
 * 作者：菠萝
 * 日期：2024/4/19 10:48
 */
@Data
@TableName("x_activity")
public class Activity {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private Double discount;
    private Double price;
    private String description;
    private Integer businessId;
    private Integer status;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime endTime;
    @TableField(exist = false)
    private String businessName;
}
