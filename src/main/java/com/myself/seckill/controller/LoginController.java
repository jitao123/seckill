package com.myself.seckill.controller;

import com.myself.seckill.service.IUserService;
import com.myself.seckill.vo.LoginVo;
import com.myself.seckill.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @description: 登录
 * @author: AT
 * @Date: 2021/1/19 2:55 下午
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/toLoginPage")
    public String toLoginPage() {
        return "login";
    }

    @PostMapping("/toLogin")
    public String doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        userService.doLogin(loginVo, request, response);
        return "redirect:/goods/toList";
    }
}
