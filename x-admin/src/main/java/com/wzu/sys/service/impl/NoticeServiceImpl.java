package com.wzu.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Notice;
import com.wzu.sys.entity.User;
import com.wzu.sys.mapper.NoticeMapper;
import com.wzu.sys.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：
 * 作者：菠萝
 * 日期：2024/4/4 14:09
 */
@Service
public class NoticeServiceImpl extends MPJBaseServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Autowired
    private NoticeMapper  noticeMapper;

    @Override
    public Result<?> add(Notice notice) {

        int insert = this.baseMapper.insert(notice);
        if(insert == 1){
            return Result.success(notice,"添加成功");
        }
        return Result.fail("添加失败");
    }

    @Override
    public Result<?> update(Notice notice) {
        if(notice.getId() == null || notice.getId() <= 0){
            return Result.fail("查询公告id不合法");
        }
        int result = this.baseMapper.updateById(notice);
        if(result == 1){
            return Result.success(notice,"更新公告成功");
        }

        return Result.fail("更新公告失败");
    }

    @Override
    public void delete(Integer id) {
        this.baseMapper.deleteById(id);
    }

    @Override
    public Result<?> deleteList(List<Integer> ids) {
        for(Integer id : ids){
            this.delete(id);
        }
        return Result.success("批量删除公告成功");
    }

    @Override
    public Result<?> selectPage(Notice notice, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper wrapper = JoinWrappers.lambda(Notice.class)
                .selectAll(Notice.class)
                .selectAs(User::getName,"userName")
                .leftJoin(User.class,"x_user",User::getId,Notice::getUserId)
                .eq(Notice::getUserId,notice.getUserId())
                .like(Notice::getTitle,notice.getTitle());
        Page page = noticeMapper.selectJoinPage(new Page(pageNum, pageSize), Notice.class, wrapper);
        if (page.getTotal() == 0) {
            return Result.fail("没有查询到你所需要的信息");
        }
        return Result.success(page, "获取所有信息成功");
    }

}
