package com.wzu.sys.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.wzu.sys.entity.Cart;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 功能：操作cart相关数据接口
 * 作者：菠萝
 * 日期：2024/4/7 17:57
 */
public interface CartMapper extends MPJBaseMapper<Cart> {
    @Select("select * from x_cart where user_id = #{userId} and goods_id = #{goodsId}")
    Cart selectByUserIdAndGoodsId(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);
}
