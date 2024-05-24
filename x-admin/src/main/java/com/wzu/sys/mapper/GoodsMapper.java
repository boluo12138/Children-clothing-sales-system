package com.wzu.sys.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.wzu.sys.entity.Goods;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 功能： 操作goods相关数据接口
 * 作者：菠萝
 * 日期：2024/4/2 13:06
 */
public interface GoodsMapper extends MPJBaseMapper<Goods> {
    @Select("select * from x_goods where business_id = #{id}")
    List<Goods> selectByBusinessId(Integer id);
    @Select("select * from x_goods where name like concat('%', #{name}, '%')")
    List<Goods> selectByName(String name);
    @Select("select * from x_goods where type_id = #{id}")
    List<Goods> selectByTypeId(Integer id);
}
