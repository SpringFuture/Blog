package com.usy.blog.controller;

import com.usy.blog.model.Type;
import com.usy.blog.service.BlogService;
import com.usy.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
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

import javax.persistence.Lob;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault
                                    (size = 6,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable,
                        Model model){
        model.addAttribute("page",typeService.listType(pageable));
        //typeService.listType(pageable);
        return "admin/types";
    }
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";

    }
    //编辑分类
    @GetMapping("/types/{id}/input")
    public String editType(@PathVariable Long id,Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }
    @PostMapping("/types")
    public String post(Type type, RedirectAttributes attributes){
        Type type1=typeService.getTypeByName(type.getName());
        if (type1!=null){
            attributes.addFlashAttribute("messages","名称已经存在");
            return "redirect:/admin/types/input";
        }else {
            Type t = typeService.saveType(type);
            if (t == null) {
                attributes.addFlashAttribute("message", "新增失败");

            } else {
                attributes.addFlashAttribute("message", "新增成功");
            }
            return "redirect:/admin/types";
        }
    }
    @PostMapping("/types/{id}")
    public String editPost(Type type, @PathVariable long id,RedirectAttributes attributes){
        Type type1=typeService.getTypeByName(type.getName());
        if (type1!=null){
            attributes.addFlashAttribute("messages","名称已经存在");
            return "redirect:/admin/types/input";
        }else {
            Type t = typeService.updateType(id,type);
            if (t == null) {
                attributes.addFlashAttribute("message", "更新失败");

            } else {
                attributes.addFlashAttribute("message", "更新成功");
            }
            return "redirect:/admin/types";
        }
    }
    @GetMapping("/types/{id}/delete")
        public String delete(@PathVariable Long id,RedirectAttributes attributes){
        System.out.println("我到这里了");
        typeService.deleteType(id);
        if (typeService.getType(id)!=null){
            attributes.addFlashAttribute("messages","这是有关联的数据无法删除");
        }else {
            attributes.addFlashAttribute("message","删除成功");
        }
        return "redirect:/admin/types";
        }
}
