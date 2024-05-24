package com.wzu.sys.service;

import com.github.yulichang.base.MPJBaseService;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Collect;

/**
 * 功能：
 * 作者：菠萝
 * 日期：2024/4/13 10:06
 */
public interface CollectService extends MPJBaseService<Collect> {
    Result selectPage(Collect collect, int pageNum, int pageSize);
}
