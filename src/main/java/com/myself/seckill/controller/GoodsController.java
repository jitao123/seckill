package com.myself.seckill.controller;

import com.myself.seckill.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: AT
 * @Date: 2021/1/20 11:29 上午
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {


    /**
     * 跳转商品页面
     * @param session
     * @param model
     * @param ticket
     * @return
     */
    @RequestMapping("/toList")
    public String toList(HttpSession session, Model model, @CookieValue("userTicket") String ticket){

        if (StringUtils.isEmpty(ticket)) {
            return "login";
        }

        User user = (User) session.getAttribute(ticket);
        if (ObjectUtils.isEmpty(user)) {
            return "login";
        }
        model.addAttribute("user",user);
        return "goodsList";
    }

}
