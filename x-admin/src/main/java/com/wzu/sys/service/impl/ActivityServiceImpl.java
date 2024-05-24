package com.wzu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Activity;
import com.wzu.sys.entity.User;
import com.wzu.sys.mapper.ActivityMapper;
import com.wzu.sys.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：促销活动服务实现类
 * 作者：菠萝
 * 日期：2024/4/19 11:53
 */
@Service
public class ActivityServiceImpl extends MPJBaseServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private UserServiceImpl userService;
    @Override
    public Result<?> selectPage(Activity activity, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<Activity> wrapper = JoinWrappers.lambda(Activity.class)
                .selectAll(Activity.class)
                .selectAs(User::getName,"businessName")
                .leftJoin(User.class, User::getId, Activity::getBusinessId)
                .like(Activity::getName, activity.getName());
        User user = userService.getUserById(activity.getBusinessId());
        if(user!= null && user.getRoleIdList().get(0) != 1){
            wrapper.eq("business_id", user.getId());
        }
        Page<Activity> page = activityMapper.selectJoinPage(new Page<>(pageNum, pageSize), Activity.class, wrapper);
        if(page.getTotal() == 0) {
            return Result.fail("没有查询到你所需要的评价信息");
        }
        return Result.success(page,"获取信息成功");

    }

    @Override
    public List<Activity> selectAll(Activity activity) {
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        User user = userService.getUserById(activity.getBusinessId());
        if (user!= null && user.getRoleIdList().get(0)!= 1){
            queryWrapper.eq("business_id", activity.getBusinessId())
                    .or().eq("business_id",1);
        }
        List<Activity> list = activityMapper.selectList(queryWrapper);
        return list;
    }

}
