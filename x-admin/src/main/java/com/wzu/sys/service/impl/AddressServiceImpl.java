package com.wzu.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Address;
import com.wzu.sys.mapper.AddressMapper;
import com.wzu.sys.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能：地址业务处理层实现类
 * 作者：菠萝
 * 日期：2024/4/12 14:01
 */
@Service
public class AddressServiceImpl extends MPJBaseServiceImpl<AddressMapper, Address> implements AddressService {
    @Autowired
    private AddressMapper addressMapper;
    /**
     * 功能：分页查询地址信息
     */
    @Override
    public Result<?> selectPage(Address address, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<Address> wrapper = JoinWrappers.lambda(Address.class)
                .eq(Address::getUserId, address.getUserId());
        Page<Address> page = addressMapper.selectJoinPage(new Page<>(pageNum, pageSize), Address.class, wrapper);
        if(page.getTotal() == 0) {
            return Result.fail("没有查询到你所需要的评价信息");
        }
        return Result.success(page,"获取信息成功");

    }

}
