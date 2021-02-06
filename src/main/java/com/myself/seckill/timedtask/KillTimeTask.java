package com.myself.seckill.timedtask;

import com.myself.seckill.entity.Goods;
import com.myself.seckill.service.IGoodsService;
import com.myself.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @descriptio * @author: AT
 * @Date: 2021/2/5 5:20 下午
 */
@Component
@Slf4j
public class KillTimeTask {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

//    @Scheduled(fixedDelay = 5000)
//    public void setKillTime(){
//        log.info("开始执行定时器～～～～");
//        List<Goods> list = goodsService.list();
//        list.forEach(goods -> {
//            redisTemplate.opsForValue().set("killID_"+goods.getId(),goods,30, TimeUnit.SECONDS);
//        });
//    }
}
