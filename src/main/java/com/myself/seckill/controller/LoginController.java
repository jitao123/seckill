package com.myself.seckill.controller;

import com.myself.seckill.service.IUserService;
import com.myself.seckill.vo.LoginVo;
import com.myself.seckill.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
//
//    @RequestMapping("/toLogin")
//    public String toLogin(){
//        return "login";
//    }

    @RequestMapping("/toLogin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo){
        RespBean respBean=  userService.doLogin(loginVo);
        return respBean;
    }
}
