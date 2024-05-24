package com.wzu.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Comment;
import com.wzu.sys.entity.Goods;
import com.wzu.sys.entity.User;
import com.wzu.sys.mapper.CommentMapper;
import com.wzu.sys.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：评论服务实现类
 * 作者：菠萝
 * 日期：2024/4/6 14:31
 */
@Service
public class CommentServiceImpl extends MPJBaseServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserServiceImpl userService;


    @Override
    public void delete(Integer id) {
        this.commentMapper.deleteById(id);
    }

    @Override
    public Result<?> deleteList(List<Integer> ids) {
        for(Integer id : ids){
            this.delete(id);
        }
        return Result.success("批量删除成功");
    }

    @Override
    public Result<?> selectPage(Comment comment, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper wrapper = JoinWrappers.lambda(Comment.class)
                .selectAll(Comment.class)
                .selectAs(User::getName,"businessName")
                .selectAs(Goods::getImg,"goodsImg")
                .selectAs(Goods::getName,"goodsName")
                .selectAs(Goods::getPrice,"goodsPrice")
                .selectAs(Goods::getUnit,"goodsUnit")
                .selectAs(User::getName,"userName")
//                .leftJoin(User.class,User::getId,Goods::getBusinessId)
                .leftJoin(User.class,User::getId,Comment::getBusinessId)
                .leftJoin(Goods.class,Goods::getId,Comment::getGoodsId)
                .leftJoin(User.class,User::getId,Comment::getUserId)
                .like(Comment::getContent,comment.getContent());
        User userById = userService.getUserById(comment.getBusinessId());
        if(userById.getRoleIdList().get(0) != 1){
            wrapper.eq("t.business_id",comment.getBusinessId());
        }
        Page page = commentMapper.selectJoinPage(new Page(pageNum, pageSize), Comment.class, wrapper);
        if (page.getTotal() == 0) {
            return Result.fail("没有查询到你所需要的信息");
        }
        return Result.success(page, "获取所有信息成功");
    }

    @Override
    public List<Comment> selectByGoodsId(Integer id) {
        return commentMapper.selectByGoodsId(id);
    }
}
