package com.myself.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: AT
 * @Date: 2021/1/18 3:46 下午
 */
@Controller
@RequestMapping("/index")
public class DemoController {

    @RequestMapping("/hello")
    public String getPage(Model model){
        model.addAttribute("name","张三");
        return "hello";
    }
}
