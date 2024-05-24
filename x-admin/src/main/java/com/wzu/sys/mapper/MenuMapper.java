package com.wzu.sys.mapper;

import com.wzu.sys.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 功能：Mapper 接口
 * 作者：菠萝
 * 日期：2024/3/17
 */
public interface MenuMapper extends BaseMapper<Menu> {
     List<Menu> getMunuListByUserId(@Param("userId") Integer userId, @Param("pid") Integer pid);
}
