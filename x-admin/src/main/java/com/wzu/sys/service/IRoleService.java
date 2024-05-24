package com.wzu.sys.service;

import com.wzu.sys.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 功能：服务类
 * 作者：菠萝
 * 日期：2024/3/17
 */
public interface IRoleService extends IService<Role> {

    void addRole(Role role);

    Role getRoleById(Integer id);

    void updateRole(Role role);

    void deleteRoleById(Integer id);
}
