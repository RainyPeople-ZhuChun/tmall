package com.rainypeople.tmall.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rainypeople.tmall.pojo.Category;
import com.rainypeople.tmall.pojo.Property;
import com.rainypeople.tmall.service.CategoryService;
import com.rainypeople.tmall.service.PropertyService;
import com.rainypeople.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PropertyController {

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("admin_property_list")
    public String list(Model model, Page page,int cid){
        List<Property> ps=propertyService.list(cid);
        Category c=categoryService.getById(cid);

        PageHelper.offsetPage(page.getStart(),page.getCount());
        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+c.getId());

        model.addAttribute("ps",ps);
        model.addAttribute("c",c);
        model.addAttribute("page",page);
        return "admin/listProperty";
    }

    @RequestMapping("admin_property_add")
    public String add(Property p){
        propertyService.add(p);
        return "redirect:/admin_property_list?cid="+p.getCid();
    }

    @RequestMapping("admin_property_delete")
    public String delete(int id){
        Property p=propertyService.getById(id);
        propertyService.delete(id);
        return  "redirect:/admin_property_list?cid="+p.getCid();
    }

    @RequestMapping("admin_property_edit")
    public String edit(int id,Model model){
        Property p=propertyService.getById(id);
        Category c= categoryService.getById(p.getCid());
        p.setCategory(c);
        model.addAttribute("p",p);
        return "admin/editProperty";
    }

    @RequestMapping("admin_property_update")
    public String updata(Property p){
        propertyService.updata(p);
        return "redirect:/admin_property_list?cid="+p.getCid();

    }

}
