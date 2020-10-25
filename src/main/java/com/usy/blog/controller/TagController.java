package com.usy.blog.controller;

import com.usy.blog.model.Tag;
import com.usy.blog.service.BlogService;
import com.usy.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags")
    public String tags(@PageableDefault
                                    (size = 6,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable,
                        Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        //typeService.listType(pageable);
        return "admin/tags";
    }
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";

    }
    @GetMapping("/tags/{id}/input")
    public String editTag(@PathVariable Long id,Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String post(Tag tag, RedirectAttributes attributes){
        Tag tag1=tagService.getTagByName(tag.getName());
        if (tag1!=null){
            attributes.addFlashAttribute("messages","标签已经存在");
            return "redirect:/admin/tags/input";
        }else {
            Tag t = tagService.saveTag(tag);
            if (t == null) {
                attributes.addFlashAttribute("message", "新增失败");

            } else {
                attributes.addFlashAttribute("message", "新增成功");
            }
            return "redirect:/admin/tags";
        }
    }
    @PostMapping("/tags/{id}")
    public String editPost(Tag tag, @PathVariable long id,RedirectAttributes attributes){
        Tag tag1=tagService.getTagByName(tag.getName());
        if (tag1!=null){
            attributes.addFlashAttribute("messages","标签已经存在");
            return "redirect:/admin/tags/input";
        }else {
            Tag t = tagService.updateTag(id,tag);
            if (t == null) {
                attributes.addFlashAttribute("message", "更新失败");

            } else {
                attributes.addFlashAttribute("message", "更新成功");
            }
            return "redirect:/admin/tags";
        }
    }
    @GetMapping("/tags/{id}/delete")
        public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        if (tagService.getTag(id)!=null){
            attributes.addFlashAttribute("messages","这是有关联的数据无法删除！");
        }else {
            attributes.addFlashAttribute("message", "删除成功");
        }
        return "redirect:/admin/tags";
        }
}
