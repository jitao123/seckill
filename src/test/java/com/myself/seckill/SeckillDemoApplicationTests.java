package com.myself.seckill;
import java.util.Date;

import com.myself.seckill.entity.Goods;
import com.myself.seckill.entity.User;
import com.myself.seckill.service.IGoodsService;
import com.myself.seckill.service.IOrderService;
import com.myself.seckill.utils.ValidatorUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeckillDemoApplicationTests {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IOrderService orderService;
    @Test
    @SuppressWarnings("all")
    void contextLoads() {
//        boolean mobile = ValidatorUtil.isMobile("16608059740");
//        System.out.println("～～～～～～～～～～～"+mobile);

        Goods goods = goodsService.getById(1);
        int updateGoods = goodsService.updateGoods(goods);
        orderService.createOrder(goods);
        System.out.println("updateGoods ～～～～～ : "+updateGoods);
    }

}
