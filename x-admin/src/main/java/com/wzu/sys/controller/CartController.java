package com.wzu.sys.controller;

import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Cart;
import com.wzu.sys.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 功能：购物车前端控制器
 * 作者：菠萝
 * 日期：2024/4/7 18:08
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result<?> selectPage(Cart cart,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        Result<?> result = cartService.selectPage(cart, pageNum, pageSize);
        result.setMessage("");
        return result;

    }
    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Cart cart) {
        return cartService.addCart(cart);
    }
    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        int i = cartService.getBaseMapper().deleteById(id);
        if(i ==1){
            return Result.success("移除成功");
        }
        return Result.success("移除失败");
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            cartService.getBaseMapper().deleteById(id);
        }
        return Result.success();
    }
}
