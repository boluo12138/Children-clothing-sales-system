package com.wzu.sys.mapper;

import com.wzu.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 功能：Mapper 接口
 * 作者：菠萝
 * 日期：2024/3/17
 */
public interface UserMapper extends BaseMapper<User> {

     List<String> getRoleNameByUserId(Integer userId);
}
