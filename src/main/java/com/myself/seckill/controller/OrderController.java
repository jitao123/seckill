package com.myself.seckill.controller;


import com.myself.seckill.entity.Order;
import com.myself.seckill.entity.User;
import com.myself.seckill.myaop.MyLogAop;
import com.myself.seckill.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2021-01-23
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    IOrderService orderService;
    /**
     * 跳转商品页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/toList")
    @MyLogAop
    public Order toList(Model model, User user) throws Exception {
        Order order =  orderService.resultOrder();
        return order;
    }
}
