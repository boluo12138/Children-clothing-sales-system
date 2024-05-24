package com.wzu.sys.service;

import com.wzu.common.vo.Result;
import com.wzu.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * 功能：服务类
 * 作者：菠萝
 * 日期：2024/3/17
 */
public interface IUserService extends IService<User> {

    Map<String, Object> login(User user);

    Map<String, Object> getUserInfo(String token);

    void logout(String token);

    void addUser(User user);

    User getUserById(Integer id);

    void updateUser(User user);

    void deleteUserById(Integer id);

    Map<String, Object> updatePassword(String token, String username, String oldPassword, String newPassword);
    Result<User> register(User user);

    User checkUserAndEmail(User user);
    Result checkCode(String code, String email);
}
