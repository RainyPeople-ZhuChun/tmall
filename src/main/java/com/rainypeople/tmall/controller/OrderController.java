package com.rainypeople.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rainypeople.tmall.pojo.Order;
import com.rainypeople.tmall.service.OrderItemService;
import com.rainypeople.tmall.service.OrderService;
import com.rainypeople.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;

    @RequestMapping("admin_order_list")
    public String list(Model model, Page page){
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Order> os=orderService.list();

        orderItemService.fill(os);

        int total= (int) new PageInfo<>(os).getTotal();
        page.setTotal(total);

        model.addAttribute("os",os);
        model.addAttribute("page",page);

        return "admin/listOrder";
    }

    @RequestMapping("admin_order_delivery")
    public String delivery(Order o){
        orderService.updata(o);
        return "redirect:/admin_order_list";
    }
}
