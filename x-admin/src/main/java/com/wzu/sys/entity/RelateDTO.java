package com.wzu.sys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelateDTO {
    /** 用户id */
    private Integer useId;
    /** 商品id */
    private Integer goodsId;
    /** 指数 */
    private Integer index;

}
