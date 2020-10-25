package com.usy.blog.service;

import com.usy.blog.model.Blog;
import com.usy.blog.model.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {

    //查询
    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    //查询分页
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    //
    Page<Blog> searchListBlog(Pageable pageable, Blog blog);
    Page<Blog> listBlog(Pageable pageable);
    //根据标签Id查询blog
    Page<Blog> listBlog(Long tagId,Pageable pageable);
    //增加
    Blog saveBlog(Blog blog);

    List<Blog> blogTop(Integer size);


    //修改
    Blog updateBlog(Long id,Blog blog);

    //删除
    void deleteBlog(Long id);
}
