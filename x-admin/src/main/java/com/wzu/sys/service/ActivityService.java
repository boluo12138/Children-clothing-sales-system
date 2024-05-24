package com.wzu.sys.service;

import com.github.yulichang.base.MPJBaseService;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Activity;

import java.util.List;

/**
 * 功能：促销活动服务接口
 * 作者：菠萝
 * 日期：2024/4/19 11:52
 */
public interface ActivityService extends MPJBaseService<Activity> {
    Result<?> selectPage(Activity activity, Integer pageNum, Integer pageSize);
    List<Activity> selectAll(Activity activity);
}
