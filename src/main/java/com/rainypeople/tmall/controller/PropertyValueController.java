package com.rainypeople.tmall.controller;

import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.PropertyValue;
import com.rainypeople.tmall.service.ProductService;
import com.rainypeople.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PropertyValueController {

    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private ProductService productService;

    @RequestMapping("admin_propertyValue_edit")
    public String edit(int pid, Model model){
        //pid是product的主键
        Product p = productService.getById(pid);
        //第一次进去产品没有属性值，需要关联
        propertyValueService.init(p);
        //查询该产品的属性值
        List<PropertyValue> pvs=propertyValueService.list(pid);

        model.addAttribute("p",p);
        model.addAttribute("pvs",pvs);

        return "admin/editPropertyValue";
    }

    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String updata(PropertyValue pv){
        String result=propertyValueService.updata(pv);
        return result;
    }
}
