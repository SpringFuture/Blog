package com.usy.blog.controller;

import com.usy.blog.model.Blog;
import com.usy.blog.service.BlogService;
import com.usy.blog.service.CommentService;
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

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired

    private CommentService commentService;

    //博客首页
    @GetMapping("/")
    public String index(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                        Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBolgs",blogService.blogTop(8));
        return "index";
    }
    //按标题搜索博客
    @PostMapping("/search")
    public String searchBlog(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable, Blog blog, Model model){
        model.addAttribute("page",blogService.searchListBlog(pageable,blog));
        //System.out.println(blogService.searchListBlog(pageable,blog));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBolgs",blogService.blogTop(8));
        //model.addAttribute("blogs",blogService.getBlog(id));
        return "index";
    }
    //博客详情
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        Blog blog=blogService.getBlog(id);
        model.addAttribute("blog",blogService.getAndConvert(id));
        model.addAttribute("comments",commentService.listCommentByBlogId(id));
        //model.addAttribute("blog",blogService.getBlog(id));
        model.addAttribute("tags",tagService.listTag(blog.getTagIds()));
        //model.addAttribute("tags",)
        return "blog";
    }
    @GetMapping("/blog/about")
    public String about(){
        return "about";
    }

}
