package com.wzu.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.*;
import com.wzu.sys.mapper.CollectMapper;
import com.wzu.sys.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能：收藏业务处理实现类
 * 作者：菠萝
 * 日期：2024/4/13 10:08
 */
@Service
public class CollectServiceImpl extends MPJBaseServiceImpl<CollectMapper, Collect> implements CollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Override
    public Result selectPage(Collect collect, int pageNum, int pageSize) {
        MPJLambdaWrapper wrapper = JoinWrappers.lambda(Collect.class)
                .selectAll(Collect.class)
//        business.name as businessName, goods.img as goodsImg,
//        goods.name as goodsName, goods.price as goodsPrice, goods.unit as goodUnit
                .selectAs(User::getName, "businessName")
                .selectAs(Goods::getImg, "goodsImg")
                .selectAs(Goods::getName, "goodsName")
                .selectAs(Goods::getPrice, "goodsPrice")
                .selectAs(Goods::getUnit, "goodUnit")
                .leftJoin(User.class,"business", User::getId, Collect::getBusinessId)
                .leftJoin(Goods.class, "goods",Goods::getId, Collect::getGoodsId)
                .eq(Collect::getUserId,collect.getUserId());
        Page page = collectMapper.selectJoinPage(new Page(pageNum, pageSize), Collect.class, wrapper);
        if (page.getTotal() == 0) {
            return Result.fail("没有查询到你所需要的信息");
        }
        return Result.success(page, "获取所有信息成功");
    }
}
