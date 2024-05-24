package com.wzu.sys.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Activity;
import com.wzu.sys.entity.Cart;
import com.wzu.sys.entity.Goods;
import com.wzu.sys.entity.User;
import com.wzu.sys.mapper.CartMapper;
import com.wzu.sys.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能：
 * 作者：菠萝
 * 日期：2024/4/7 18:06
 */
@Service
public class CartServiceImpl extends MPJBaseServiceImpl<CartMapper, Cart> implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Override
    public Result<?> selectPage(Cart cart, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<Cart> wrapper = JoinWrappers.lambda(Cart.class)
                .selectAll(Cart.class)  //查询Cart表全部字段
                .selectAs(User::getUsername,"businessName")  //查询Username 别名businessName
                .selectAs(Goods::getImg,"goodsImg")
                .selectAs(Goods::getName,"goodsName")
                .selectAs(Goods::getPrice,"goodsPrice")
                .selectAs(Goods::getUnit,"goodsUnit")
                .selectAs(Activity::getDiscount,"activityDiscount")
                .selectAs(Activity::getPrice,"activityPrice")
                .selectAs(Activity::getStatus,"activityStatus")
                .eq(Cart::getUserId,cart.getUserId())
                .leftJoin(User.class,"x_user",User::getId,Cart::getBusinessId)
                .leftJoin(Goods.class,"x_goods",Goods::getId,Cart::getGoodsId)
                .leftJoin(Activity.class,"x_activity",Activity::getId,Goods::getActivityId)
//                .eq(Cart::getId,cart.getId())
                .eq(Cart::getUserId,cart.getUserId());
        Page<Cart> page = cartMapper.selectJoinPage(new Page<>(pageNum, pageSize), Cart.class, wrapper);
        if(page.getTotal() == 0) {
            return Result.fail("没有查询到你所需要的评价信息");
        }
        return Result.success(page,"获取信息成功");
    }

    @Override
    public Result<?> addCart(Cart cart) {
        // 判断该用户对该商品有没有加入过购物车，如果加入过，那么只要更新一下该条记录的num（+1）
        Cart dbCart = cartMapper.selectByUserIdAndGoodsId(cart.getUserId(), cart.getGoodsId());
        int result;
        if (ObjectUtil.isNotEmpty(dbCart)) {
            dbCart.setNum(dbCart.getNum() + 1);
             result = cartMapper.updateById(dbCart);
        } else {
             result = cartMapper.insert(cart);
        }
        if(result == 1){
            return Result.success("加入购物车成功");
        }
        return Result.fail("加入购物车失败");
    }

}
