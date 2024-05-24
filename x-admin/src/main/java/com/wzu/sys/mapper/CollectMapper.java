package com.wzu.sys.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.wzu.sys.entity.Collect;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 功能：操作collect相关数据接口
 * 作者：菠萝
 * 日期：2024/4/13 10:02
 */
public interface CollectMapper extends MPJBaseMapper<Collect> {
    @Select("select * from collect where user_id = #{userId} and goods_id = #{goodsId}")
    Collect selectByUserIdAndGoodsId(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);
}
