package com.wzu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.*;
import com.wzu.sys.mapper.OrdersMapper;
import com.wzu.sys.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：订单业务处理
 * 作者：菠萝
 * 日期：2024/4/5 23:50
 */
@Service
public class OrderServiceImpl extends MPJBaseServiceImpl<OrdersMapper, Orders> implements OrderService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private UserServiceImpl userService;
    @Override
    public Result<?> update(Orders orders) {
        if(orders.getId() == null || orders.getId() <= 0){
            return Result.fail("修改商品id不合法");
        }
        int result = this.ordersMapper.updateById(orders);
        if(result == 1){
            return Result.success(orders,"更新成功");
        }
        return Result.fail("更新失败");
    }

    @Override
    public void delete(Integer id) {
        this.ordersMapper.deleteById(id);
    }

    @Override
    public Result<?> deleteList(List<Integer> ids) {
        for(Integer id : ids){
            this.delete(id);
        }
        return Result.success("批量删除成功");
    }

    @Override
    public Result<?> selectPage(Orders orders, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper wrapper = JoinWrappers.lambda(Orders.class)
                .selectAll(Orders.class)
                .selectAs(User::getName,"businessName")
                .selectAs(Goods::getImg,"goodsImg")
                .selectAs(Goods::getName,"goodsName")
                .selectAs(Goods::getPrice,"goodsPrice")
                .selectAs(Goods::getUnit,"goodsUnit")
                .selectAs(Address::getUsername,"username")
                .selectAs(Address::getUseraddress,"useraddress")
                .selectAs(Address::getPhone,"phone")
                .leftJoin(User.class,"business",User::getId,Orders::getUserId)
                .leftJoin(Goods.class,"goods",Goods::getId,Orders::getGoodsId)
                .leftJoin(Address.class,"address",Address::getId,Orders::getAddressId)
                .like(Orders::getOrderId,orders.getOrderId());
//                        .eq(Orders::getBusinessId,orders.getBusinessId());
        if(orders.getBusinessId() != null && orders.getBusinessId() > 0){
            User userById = userService.getUserById(orders.getBusinessId());
            System.out.println(userById.toString());
            if(userById.getRoleIdList().get(0) == 2){
                wrapper.eq("t.business_id",orders.getBusinessId()); //查询的是表的别名，在插件中表orders 别名为 t
            }
        }
        if(orders.getUserId() != null && orders.getUserId() > 0){
            wrapper.eq("t.user_id",orders.getUserId()); //查询的是表的别名，在插件中表orders 别名为 t
        }
        Page page = ordersMapper.selectJoinPage(new Page(pageNum, pageSize), Orders.class, wrapper);
        if (page.getTotal() == 0) {
            return Result.fail("没有查询到你所需要的信息");
        }
        return Result.success(page, "获取所有信息成功");
    }

    @Override
    public Orders selectByOrderID(String oid) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id",oid);
        return ordersMapper.selectOne(wrapper);
    }

}
