package com.usy.blog.controller;

import com.usy.blog.model.Blog;
import com.usy.blog.model.BlogQuery;
import com.usy.blog.model.Type;
import com.usy.blog.service.BlogService;
import com.usy.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BlogTypesController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/blog/types/{id}")
    public String blogTypes(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model){
        List<Type> typeList =typeService.listTypeTop(10000);
        if (id==-1){
            id=typeList.get(0).getId();
        }
        BlogQuery blogQuery=new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",typeList);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
    @PostMapping("/blog/types/search")
    public String searchBlog(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable, Blog blog, Model model){
        List<Type> typeList =typeService.listTypeTop(10000);
        Long id=typeList.get(0).getId();
        model.addAttribute("types",typeList);
        model.addAttribute("activeTypeId",id);
        model.addAttribute("page",blogService.searchListBlog(pageable,blog));
        return "types";
    }

}
