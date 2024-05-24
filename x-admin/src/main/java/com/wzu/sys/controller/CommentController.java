package com.wzu.sys.controller;

import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Comment;
import com.wzu.sys.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 功能：评论前端控制器
 * 作者：菠萝
 * 日期：2024/4/6 14:34
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result<?> selectPage(Comment comment,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        Result<?> page = commentService.selectPage(comment, pageNum, pageSize);
        return Result.success(page,"获取所有公告评价信息成功");
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {

        commentService.delete(id);

        return Result.success("删除成功");
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return commentService.deleteList(ids);
    }

    @GetMapping("/selectByGoodsId")
    public Result selectByGoodsId(@RequestParam Integer id) {
        List<Comment> list =  commentService.selectByGoodsId(id);
        return Result.success(list);
    }
    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Comment comment) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        // 将格式化后的日期时间设置到comment中
        comment.setTime(formattedDateTime);
        int insert = commentService.getBaseMapper().insert(comment);
        if (insert > 0) {
            return Result.success("新增成功");
        }
        return Result.fail("新增失败");
    }

}
