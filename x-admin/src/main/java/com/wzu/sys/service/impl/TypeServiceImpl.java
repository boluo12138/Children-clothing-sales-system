package com.wzu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.wzu.common.vo.Result;
import com.wzu.sys.entity.Address;
import com.wzu.sys.entity.Menu;
import com.wzu.sys.entity.Type;
import com.wzu.sys.mapper.TypeMapper;
import com.wzu.sys.service.TypeService;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 * 作者：菠萝
 * 日期：2024/4/13 15:56
 */
@Service
public class TypeServiceImpl extends MPJBaseServiceImpl<TypeMapper, Type> implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<Type> getAllType(String description) {
        // 一级菜单
        LambdaQueryWrapper<Type> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Type::getParentId,0)
                .like(Type::getDescription,description);
        List<Type> typeList = typeMapper.selectList(wrapper);
        // 填充子菜单
        setTypeChildren(typeList);
        System.out.println("--------------------------");
        System.out.println(typeList.toString());
        System.out.println("--------------------------");
        return typeList;
    }

    @Override
    public Result selectPage(Type type, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<Type> wrapper = JoinWrappers.lambda(Type.class)
                .like(Type::getName,type.getName());
        Page<Type> typePage = typeMapper.selectJoinPage(new Page<>(pageNum, pageSize), Type.class, wrapper);
        return Result.success(typePage);
    }


    @Override
    public void deleteTypeChilren(Type deleteType) {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", deleteType.getId());
        List<Type> types = typeMapper.selectList(wrapper);

        if (types != null && types.size() > 0) {
            for (Type type : types) {
                deleteTypeChilren(type);
            }
        }
        typeMapper.deleteById(deleteType.getId());
    }

    @Override
    public Type selectById(Integer id) {
        Type type = typeMapper.selectById(id);
        Type parentType = typeMapper.selectById(type.getParentId());
        parentType.setChildren(new ArrayList<>());
        // 将type对象添加到parentType的children列表
        parentType.getChildren().add(type);
        return parentType;
    }

    private void setTypeChildren(List<Type> typeList) {
        if(typeList != null){
            for (Type type : typeList) {
                LambdaQueryWrapper<Type> subWrapper = new LambdaQueryWrapper<>();
                subWrapper.eq(Type::getParentId,type.getId());
                List<Type> subTypeList = typeMapper.selectList(subWrapper);
                type.setChildren(subTypeList);
                // 递归
                setTypeChildren(subTypeList);
            }
        }
    }
}
