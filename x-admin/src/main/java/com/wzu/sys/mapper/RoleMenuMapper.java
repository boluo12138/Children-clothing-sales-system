package com.wzu.sys.mapper;

import com.wzu.sys.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 功能：Mapper 接口
 * 作者：菠萝
 * 日期：2024/3/17
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
     List<Integer> getMenuIdListByRoleId(Integer roleId);
}
