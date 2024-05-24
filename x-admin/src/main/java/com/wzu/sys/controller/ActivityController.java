package com.wzu.sys.controller;

import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Activity;
import com.wzu.sys.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：促销活动前端控制器
 * 作者：菠萝
 * 日期：2024/4/19 11:59
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Activity activity,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Result<?> result = activityService.selectPage(activity, pageNum, pageSize);
        return result;
    }
    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Activity activity) {
        List<Activity> list = activityService.selectAll(activity);
        if(list == null || list.size() == 0){
            return Result.fail("查询活动失败");
        }
        return Result.success(list, "查询活动成功");
    }
    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Activity activity) {
        int i = activityService.getBaseMapper().insert(activity);
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
        int i = activityService.getBaseMapper().deleteById(id);
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
        int i = activityService.getBaseMapper().deleteBatchIds(ids);
        if(i == 0){
            return Result.fail("批量删除活动失败");
        }
        return Result.success("批量删除活动成功");
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Activity activity) {
        boolean b = activityService.updateById(activity);
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
        Activity activity = activityService.getById(id);
        return Result.success(activity);
    }
}
