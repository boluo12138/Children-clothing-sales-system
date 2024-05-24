package com.wzu.sys.service;

import com.github.yulichang.base.MPJBaseService;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Cart;

public interface CartService extends MPJBaseService<Cart> {
    Result<?> selectPage(Cart cart, Integer pageNum, Integer pageSize);
    Result<?> addCart(Cart cart);
}
