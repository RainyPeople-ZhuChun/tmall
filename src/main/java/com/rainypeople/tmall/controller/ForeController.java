package com.rainypeople.tmall.controller;

import com.rainypeople.tmall.pojo.*;
import com.rainypeople.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ForeController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;

    @RequestMapping("forehome")
    public String home(Model model){
        List<Category> cs = categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);

        model.addAttribute("cs",cs);

        return "fore/home";
    }

    @RequestMapping("foreregister")
    public String register(User user,Model model){
        String name=user.getName();
        name= HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean flag=userService.isExist(name);
        if (flag){
            String msg="用户名已经被使用,请重新输入用户名";
            model.addAttribute("msg",msg);
            model.addAttribute("user",null);
            return "fore/register";
        }
        userService.add(user);
        return "fore/registerSuccess";
    }

    @RequestMapping("forelogin")
    public String login(@RequestParam("name")String name, @RequestParam("password")String password, Model model, HttpSession session){
        name=HtmlUtils.htmlEscape(name);
        boolean exist = userService.isExist(name);
        if (!exist){
            model.addAttribute("msg","帐号不存在，请注册帐号！");
            return "fore/login";
        }
        User user=userService.get(name,password);
        if (null==user){
            String msg="账号密码错误";
            model.addAttribute("msg",msg);
            return "fore/login";
        }
        session.setAttribute("user",user);
        return "redirect:/forehome";
    }

    @RequestMapping("forelogout")
    public String loginOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/forehome";
    }

    @RequestMapping("foreproduct")
    public String product(int pid,Model model){
        Product p = productService.getById(pid);

        Category category = categoryService.getById(p.getCid());
        p.setCategory(category);

        List<ProductImage> productSingleImages = productImageService.list(pid, productImageService.type_single);
        List<ProductImage> productDetailImages = productImageService.list(pid, productImageService.type_detail);
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);
        productService.setFirstProductImage(p);

        List<PropertyValue> pvs = propertyValueService.list(p.getId());
        List<Review> reviews = reviewService.list(pid);
        productService.setSaleAndReviewNumber(p);

        model.addAttribute("p",p);
        model.addAttribute("pvs",pvs);
        model.addAttribute("reviews",reviews);

        return "fore/product";
    }

    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session){
        User user = (User) session.getAttribute("user");
        if (null==user){
            return "fail";
        }
        return "success";
    }

    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name")String name,
                            @RequestParam("password")String password,
                            HttpSession session){
        name=HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);
        if (null==user){
            return "fail";
        }
        session.setAttribute("user",user);
        return "success";
    }


}
