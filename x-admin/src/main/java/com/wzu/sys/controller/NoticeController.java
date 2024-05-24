package com.wzu.sys.controller;

import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Notice;
import com.wzu.sys.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 功能：公告信息表前端操作接口
 * 作者：菠萝
 * 日期：2024/3/31 14:57
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody Notice notice) {
        System.out.println(notice.toString());
        notice.setTime(String.valueOf(LocalDate.now()));
        Result<?> add = noticeService.add(notice);
        return add;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {

        noticeService.delete(id);

        return Result.success("删除成功");
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return noticeService.deleteList(ids);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result<?> updateById(@RequestBody Notice notice) {
        Result<?> update = noticeService.update(notice);
        return update;
    }
//
//    /**
//     * 根据ID查询
//     */
//    @GetMapping("/selectById/{id}")
//    public Result selectById(@PathVariable Integer id) {
//        Notice notice = noticeService.selectById(id);
//        return Result.success(notice);
//    }
//

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Notice> list = noticeService.list();
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result<?> selectPage(Notice notice,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        Result<?> result = noticeService.selectPage(notice, pageNum, pageSize);
        return result;
    }

}
