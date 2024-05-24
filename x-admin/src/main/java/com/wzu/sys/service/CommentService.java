package com.wzu.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.base.MPJBaseService;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Comment;
import com.wzu.sys.entity.Goods;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 功能：收藏业务处理
 * 作者：菠萝
 * 日期：2024/4/6 14:30
 */
public interface CommentService extends MPJBaseService<Comment> {

    void delete(Integer id);
    Result<?> deleteList(List<Integer> ids);
    Result<?> selectPage(Comment comment, Integer pageNum, Integer pageSize);

    List<Comment> selectByGoodsId(Integer id);
}
