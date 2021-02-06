package com.myself.seckill.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.myself.seckill.constant.SeckillStatusEnum;
import com.myself.seckill.entity.User;
import com.myself.seckill.service.IGoodsService;
import com.myself.seckill.service.IUserService;
import com.myself.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @description:
 * @author: AT
 * @Date: 2021/1/20 11:29 上午
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    /**
     * 跳转商品页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/toList")
    public String toList(Model model, User user) throws Exception {
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        return "goodsList";
    }


    @RequestMapping("/getGoodsDetil/{goodsId}")
    public String getGoodsDetil(Model model, User user, @PathVariable("goodsId") Long goodsId) throws Exception {
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.findGoodsVoById(goodsId);
        int status = SeckillStatusEnum.NO_DRAW.getStatus();
        Date startDate = goods.getStartDate();
        Date endDate = goods.getEndDate();
        Date now = new Date();
        if (now.before(startDate)) {

        }else if (now.after(endDate)){
            status=SeckillStatusEnum.SECKILL.getStatus();
        }else {
            status=SeckillStatusEnum.OVER.getStatus();
        }
        model.addAttribute("goods", goods);
        model.addAttribute("status", status);
        return "getGoodsDetail";
    }



}
