package com.usy.blog.service;

import com.usy.blog.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    //新增
    Tag saveTag(Tag tag);
    //查询
    Tag getTag(Long id);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    //根据名称查
    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);
    //修改
    Tag updateTag(Long id,Tag tag);
    //删除
    void deleteTag(Long id);

    List<Tag> listTagTop(Integer size);
}
