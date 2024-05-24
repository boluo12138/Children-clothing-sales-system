package com.wzu.sys.service;

import com.github.yulichang.base.MPJBaseService;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Type;

import java.util.List;

/**
 * 功能：类型服务接口
 * 作者：菠萝
 * 日期：2024/4/13 15:51
 */
public interface TypeService extends MPJBaseService<Type> {
    List<Type> getAllType(String description);
    Result selectPage(Type type, Integer pageNum, Integer pageSize);
    void deleteTypeChilren(Type deleteType);

    Type selectById(Integer id);
}
