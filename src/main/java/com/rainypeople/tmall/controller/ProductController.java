package com.rainypeople.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rainypeople.tmall.pojo.Category;
import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.service.CategoryService;
import com.rainypeople.tmall.service.ProductService;
import com.rainypeople.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("admin_product_list")
    public String list(int cid, Model model, Page page){
        Category c = categoryService.getById(cid);

        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Product> ps = productService.list(cid);

        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+c.getId());

        model.addAttribute("ps", ps);
        model.addAttribute("c", c);
        model.addAttribute("page", page);

        return "admin/listProduct";
    }


    @RequestMapping("admin_product_add")
    public String add(Product p){
        p.setCreateDate(new Date());
        productService.add(p);
        return "redirect:/admin_product_list?cid="+p.getCid();
    }

    @RequestMapping("admin_product_delete")
    public String delete(int id){
        Product p=productService.getById(id);
        productService.delete(id);
        return "redirect:/admin_product_list?cid="+p.getCid();
    }

    @RequestMapping("admin_product_edit")
    public String edit(int id,Model model){
        Product p = productService.getById(id);
        Category c = categoryService.getById(p.getCid());
        p.setCategory(c);

        model.addAttribute("p",p);
        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String updata(Model model,Product p){
        productService.updata(p);
        return "redirect:/admin_product_list?cid="+p.getCid();
    }
}




















