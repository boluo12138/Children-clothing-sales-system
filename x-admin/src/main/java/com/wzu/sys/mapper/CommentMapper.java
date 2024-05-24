package com.wzu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.wzu.sys.entity.Comment;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper extends MPJBaseMapper<Comment> {
    @Select("select comment.*, user.avatar as userAvatar, user.name as userName " +
            "from x_comment as comment left join x_user as user on comment.user_id = user.id where comment.goods_id = #{id}")
    List<Comment> selectByGoodsId(Integer id);
}
