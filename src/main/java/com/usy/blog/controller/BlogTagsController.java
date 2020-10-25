package com.usy.blog.controller;

import com.usy.blog.model.Blog;
import com.usy.blog.model.Tag;
import com.usy.blog.model.Type;
import com.usy.blog.service.BlogService;
import com.usy.blog.service.TagService;
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
public class BlogTagsController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blog/tags/{id}")
    public String blogTags(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model){
        List<Tag> tagList =tagService.listTagTop(10000);
        if (id==-1){
            id=tagList.get(0).getId();
        }
        model.addAttribute("tags",tagList);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return "tags";
    }
    @PostMapping("/blog/tags/search")
    public String searchBlog(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable, Blog blog, Model model){
        List<Tag> tagList =tagService.listTagTop(10000);
        Long id=tagList.get(0).getId();
        model.addAttribute("tags",tagList);
        model.addAttribute("activeTagId",id);
        model.addAttribute("page",blogService.searchListBlog(pageable,blog));
        return "tags";
    }

}
