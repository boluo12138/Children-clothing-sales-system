package com.wzu.sys.controller;

import cn.hutool.core.date.DateUtil;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Cart;
import com.wzu.sys.entity.Orders;
import com.wzu.sys.service.CartService;
import com.wzu.sys.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

/**
 * 功能：订单前端控制器
 * 作者：菠萝
 * 日期：2024/4/5 23:53
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result<?> selectPage(Orders orders,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        System.out.println("Orders: " + orders.toString());
        return orderService.selectPage(orders, pageNum, pageSize);
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Orders orders) {
        orders.setOrderId(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        for (Cart cart : orders.getCartData()) {
            Orders dbOrders = new Orders();
            BeanUtils.copyProperties(orders, dbOrders);
            dbOrders.setGoodsId(cart.getGoodsId());
            dbOrders.setBusinessId(cart.getBusinessId());
            dbOrders.setNum(cart.getNum());
            dbOrders.setPrice(cart.getNum() * cart.getGoodsPrice());
            orderService.getBaseMapper().insert(dbOrders);
            // 把购物车里对应的商品删掉
            cartService.getBaseMapper().deleteById(cart.getId());
        }
        return Result.success("新增成功");
    }
    /**
     * 修改
     */
    @PutMapping("/update")
    public Result<?> updateById(@RequestBody Orders order) {
        Result<?> update = orderService.update(order);
        return update;
    }
    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        orderService.delete(id);
        return Result.success("删除成功");
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return orderService.deleteList(ids);
    }

}
