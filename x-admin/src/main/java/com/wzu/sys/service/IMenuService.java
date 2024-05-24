package com.wzu.sys.service;

import com.wzu.sys.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 功能：服务类
 * 作者：菠萝
 * 日期：2024/3/17
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getAllMenu();

    List<Menu> getMenuListByUserId(Integer userId);
    void deleteMenuChilren(Menu deletedmenu);
}
