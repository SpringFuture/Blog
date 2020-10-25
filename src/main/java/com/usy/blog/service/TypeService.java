package com.usy.blog.service;

import com.usy.blog.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    //新增
    Type saveType(Type type);
    //查询
    Type getType(Long id);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);

    //根据名称查
    Type getTypeByName(String name);

    Page<Type> listType(Pageable pageable);
    //修改
    Type updateType(Long id,Type type);
    //删除
    void deleteType(Long id);

}
