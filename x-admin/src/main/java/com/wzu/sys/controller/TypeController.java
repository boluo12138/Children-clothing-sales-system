package com.wzu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Type;
import com.wzu.sys.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：商品类型前端控制器
 * 作者：菠萝
 * 日期：2024/4/13 16:07
 */
@RestController
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @GetMapping("/getAllType")
    public Result<List<Type>> getAllMenu(@RequestParam(value = "description") String description){
        System.out.println("调用了getAllMenu");
        List<Type> typeList = typeService.getAllType(description);
        return Result.success(typeList);
    }
    @GetMapping("/selectAll")

    public Result<List<Type>> selectAll(){
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.lambda().ne(Type::getParentId, 0);
        List<Type> typeList = typeService.list(wrapper);
        return Result.success(typeList);
    }
    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Type type,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        return typeService.selectPage(type, pageNum, pageSize);
    }
    /**
     * 新增
     */
    @PostMapping("/add")
    public Result insert(@RequestBody Type type) {
        return typeService.getBaseMapper().insert(type) > 0? Result.success() : Result.fail("新增失败");
    }
    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody Type type) {
        return typeService.getBaseMapper().updateById(type) > 0? Result.success() : Result.fail("修改失败");
    }
    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result<Type> delete(@PathVariable Integer id){
        Type type1 = typeService.getBaseMapper().selectById(id);
        typeService.deleteTypeChilren(type1);
        return Result.success(type1);
    }
    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            Type type1 = typeService.getBaseMapper().selectById(id);
            typeService.deleteTypeChilren(type1);
        }
        return Result.success("批量删除地址成功");
    }
    /**
     * 根据ID查询
     */
    @GetMapping("/selectById")
    public Result selectById(@RequestParam Integer id) {
        Type type = typeService.selectById(id);
        return Result.success(type);
    }
}
