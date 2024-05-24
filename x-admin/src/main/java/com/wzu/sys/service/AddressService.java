package com.wzu.sys.service;

import com.github.yulichang.base.MPJBaseService;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Address;


public interface AddressService extends MPJBaseService<Address> {
    Result<?> selectPage(Address address, Integer pageNum, Integer pageSize);

}
