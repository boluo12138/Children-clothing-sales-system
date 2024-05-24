package com.wzu.sys.controller;

import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Goods;
import com.wzu.sys.service.GoodsService;
import com.wzu.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：商品前端控制器
 * 作者：菠萝
 * 日期：2024/4/2 16:31
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private IUserService userSer;

    // 新增
    @PostMapping("/add")
    public Result add(@RequestBody Goods goods) {
        System.out.println(goods.toString());
        Result<?> add = goodsService.add(goods);
        return add;
    }
    // 删除
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        goodsService.delete(id);
        return Result.success("删除成功");
    }

    // 批量删除
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return goodsService.deleteList(ids);
    }

    // 修改
    @PutMapping("/update")
    public Result updateById(@RequestBody Goods goods) {
        Result<?> update = goodsService.update(goods);
        return update;
    }

    // 分页查询
    @GetMapping("/selectPage")
    public Result<?> selectPage(Goods goods,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Result<?> result = goodsService.selectPage(goods, pageNum, pageSize);
        return result;
    }
    @GetMapping("/selectByBusinessId")
    public Result selectByBusinessId(@RequestParam Integer id) {
        List<Goods> list = goodsService.selectByBusinessId(id);
        return Result.success(list);
    }
    @GetMapping("/selectById")
    public Result selectById(@RequestParam Integer id) {
        Goods goods = goodsService.selectById(id);
        return Result.success(goods);
    }
    @GetMapping("/selectByName")
    public Result selectByName(@RequestParam String name) {
        List<Goods> list = goodsService.selectByName(name);
        return Result.success(list);
    }
    @GetMapping("/selectByTypeId")
    public Result selectByTypeId(@RequestParam Integer id) {
        List<Goods> list = goodsService.selectByTypeId(id);
        return Result.success(list);
    }
}
