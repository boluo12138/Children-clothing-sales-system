package com.wzu.sys.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Collect;
import com.wzu.sys.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 功能：收藏前端控制器
 * 作者：菠萝
 * 日期：2024/4/13 10:13
 */
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private CollectService collectService;

    @PostMapping("/add")
    public Result addCollect(@RequestBody Collect collect) {
        Collect dbCollect = collectService.getBaseMapper().selectOne(new QueryWrapper<>(collect)
                .eq("user_id", collect.getUserId()).eq("goods_id", collect.getGoodsId()));
        if (ObjectUtil.isNotEmpty(dbCollect)) {
            return Result.fail( "您已收藏过该商品，请勿重复收藏");
        }
        collectService.getBaseMapper().insert(collect);
        return Result.success("收藏成功");
    }
    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result<?> selectPage(Collect collect,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        System.out.println("Orders: " + collect.toString());
        return collectService.selectPage(collect, pageNum, pageSize);
    }
    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        collectService.getBaseMapper().deleteById(id);
        return Result.success("移除成功");
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            collectService.getBaseMapper().deleteById(id);
        }
        return Result.success("批量移除成功");
    }
}
