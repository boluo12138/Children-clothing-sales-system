package com.wzu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Address;
import com.wzu.sys.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：地址前端控制器
 * 作者：菠萝
 * 日期：2024/4/12 14:03
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Address address,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Result<?> result = addressService.selectPage(address, pageNum, pageSize);
        return result;
    }
    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Address address) {
        System.out.println("address: "+address.toString());
        List<Address> list = addressService.getBaseMapper().
                selectList(new QueryWrapper<>(address).
                        eq("user_id",address.getUserId()));
        return Result.success(list);
    }
    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Address address) {
        int i = addressService.getBaseMapper().insert(address);
        if(i == 1){
            return Result.success("添加地址成功");
        }
        return Result.fail("添加地址失败");
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        int i = addressService.getBaseMapper().deleteById(id);
        if(i == 1){
            return Result.success("删除地址成功");
        }
        return Result.fail("删除地址失败");
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            addressService.getBaseMapper().deleteById(id);
        }
        return Result.success("批量删除地址成功");
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Address address) {
        boolean b = addressService.updateById(address);
        if(b == true){
            return Result.success("修改地址成功");
        }
        return Result.fail("修改地址失败");
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Address address = addressService.getById(id);
        return Result.success(address);
    }
}
