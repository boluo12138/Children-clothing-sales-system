package com.wzu.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.base.MPJBaseService;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Notice;
import java.util.List;

/**
 * 公告信息表业务处理
 **/

public interface NoticeService extends MPJBaseService<Notice> {
    Result<?>  add(Notice notice);
    Result<?> update(Notice notice);
    void delete(Integer id);
    Result<?> deleteList(List<Integer> ids);
    Result<?> selectPage(Notice notice, Integer pageNum, Integer pageSize);
}
