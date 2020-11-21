package com.rainypeople.tmall.controller;

import com.rainypeople.tmall.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("registerPage")
    public String registerPage(){
        return "fore/register";
    }

    @RequestMapping("loginPage")
    public String login(){
        return "fore/login";
    }
}
