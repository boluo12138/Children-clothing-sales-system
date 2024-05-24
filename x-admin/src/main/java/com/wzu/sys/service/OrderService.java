package com.wzu.sys.service;

import com.github.yulichang.base.MPJBaseService;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Orders;
import java.util.List;

/**
 * 功能：
 * 作者：菠萝
 * 日期：2024/4/5 23:49
 */
public interface OrderService extends MPJBaseService<Orders> {

    Result<?> update(Orders orders);

    void delete(Integer id);

    Result<?> deleteList(List<Integer> ids);
    Result<?> selectPage(Orders orders,Integer pageNum,Integer pageSize);
    Orders selectByOrderID(String oid);
}
