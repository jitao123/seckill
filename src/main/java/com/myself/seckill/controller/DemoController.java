package com.myself.seckill.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.myself.seckill.entity.Goods;
import com.myself.seckill.exception.BusinessException;
import com.myself.seckill.service.IGoodsService;
import com.myself.seckill.service.IOrderService;
import com.myself.seckill.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: ATCo @Date: 2021/1/18 3:46 下午
 */
@RestController
@RequestMapping("/index")
@Slf4j
public class DemoController {

    // 设置等待个数 令牌桶算法
    private RateLimiter rateLimiter = RateLimiter.create(30);

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 悲观锁 pessimisticLock 库存
    @RequestMapping("/pessimisticLock")
    public String pessimisticLock(int goodsId) throws Exception {
        //校验商品库存
        Goods goods = goodsService.checkGoodsStock(goodsId);
        //修改销售额度
        if (goodsService.updateGoods(goods) < 1) throw new BusinessException("抢购失败～～");
        // 创建订单
        orderService.createOrder(goods);
        return "订单保存成功～～～";
    }


    @RequestMapping("/acquire")
    public String acquire(int goodsId) throws Exception {
        // 排队等待
        log.info("等待时间 ～～ " + rateLimiter.acquire());
        //校验商品库存
        Goods goods = goodsService.checkGoodsStock(goodsId);
        //修改销售额度
        if (goodsService.updateGoods(goods) < 1) throw new BusinessException("抢购失败～～");
        // 创建订单
        orderService.createOrder(goods);
        return "订单保存成功～～～";
    }

    @RequestMapping("/tryAcquire")
    public String tryAcquire(int goodsId) throws Exception {
        //设置个等待时间,如果在 等待的时间内获取到了 token令牌,则处理业务 ，如果在 等待时间内没有获取到响应token则抛弃
        if (!rateLimiter.tryAcquire(5, TimeUnit.SECONDS)) {
            System.out.println("当前请求被限流了");
            return "";
        }
        //校验商品库存
        Goods goods = goodsService.checkGoodsStock(goodsId);
        //修改销售额度
        if (goodsService.updateGoods(goods) < 1) throw new BusinessException("抢购失败～～");
        // 创建订单
        orderService.createOrder(goods);
        return "订单保存成功～～～";
    }

    // 限制时间内访问 limitedTimeAccess
    @RequestMapping("/limitedTimeAccess")
    public String limitedTimeAccess(int goodsId) throws Exception {

        // 使用定时器，在每天的几点到几点内，将 kill_ID +商品ID 存入到redis，并设置过期时间
//        redisTemplate.opsForValue().("kill_id"+goods)
        if (!redisTemplate.hasKey("kill_id" + goodsId)) {
            throw new BusinessException(" 不在抢购时间内～～～～");
        }
        //设置个等待时间,如果在 等待的时间内获取到了 token令牌,则处理业务 ，如果在 等待时间内没有获取到响应token则抛弃
        if (!rateLimiter.tryAcquire(5, TimeUnit.SECONDS)) {
            throw new BusinessException(" 抢购太火爆，请稍后重试～～～～");
        }
        //校验商品库存
        Goods goods = goodsService.checkGoodsStock(goodsId);
        //修改销售额度
        if (goodsService.updateGoods(goods) < 1) throw new BusinessException("抢购失败～～");
        // 创建订单
        orderService.createOrder(goods);
        return "测试令牌桶";
    }

    // 限制时间内访问 验证用户信息 VerifyUserInformation
    @RequestMapping("/limitedTimeAndVerifyUserInformationAccess")
    public String limitedTimeAndVerifyUserInformationAccess(@RequestParam("goodsId") int goodsId, @RequestParam("userId") int userId, @RequestParam("md5") String md5) throws Exception {
        // 使用定时器，在每天的几点到几点内，将 kill_ID +商品ID 存入到redis，并设置过期时间
        if (!redisTemplate.hasKey("kill_id" + goodsId)) {
            throw new BusinessException(" 不在抢购时间内～～～～");
        }
        // 1,验证用户的md5 是否存在
        Object mdtObject = redisTemplate.opsForValue().get("userMd5" + goodsId + ":" + userId);
        if (ObjectUtils.isEmpty(mdtObject)) {
            throw new BusinessException(" 验证消息不能为空！");
        }
        if (!md5.equals((String) mdtObject)) throw new BusinessException("非法的验证消息～～");

        //设置个等待时间,如果在 等待的时间内获取到了 token令牌,则处理业务 ，如果在 等待时间内没有获取到响应token则抛弃
        if (!rateLimiter.tryAcquire(5, TimeUnit.SECONDS)) {
            throw new BusinessException(" 抢购太火爆，请稍后重试～～～～");
        }
        //校验商品库存
        Goods goods = goodsService.checkGoodsStock(goodsId);
        //修改销售额度
        if (goodsService.updateGoods(goods) < 1) throw new BusinessException("抢购失败～～");
        // 创建订单
        orderService.createOrder(goods);
        return "购买成功";
    }


    // 限制访问次数 LimitTheNumberOfVisits

    // 限制时间内访问 验证用户信息 VerifyUserInformation
    @RequestMapping("/LimitTheNumberOfVisits")
    public String LimitTheNumberOfVisits(@RequestParam("goodsId") int goodsId, @RequestParam("userId") int userId, @RequestParam("md5") String md5) throws Exception {

        // 使用定时器，在每天的几点到几点内，将 kill_ID +商品ID 存入到redis，并设置过期时间
        if (!redisTemplate.hasKey("kill_id" + goodsId)) {
            throw new BusinessException(" 不在抢购时间内～～～～");
        }

        // 设置验证访问次数
        // 1，第一次访问在redis 中获取 requestNumber
        String requestUserStr = "requestNumber:" + userId;
        if (!redisTemplate.hasKey(requestUserStr)) {
            redisTemplate.opsForValue().set(requestUserStr, 1, 120, TimeUnit.SECONDS);
        } else {
            int sum = (Integer) redisTemplate.opsForValue().get(requestUserStr);
            if (sum >10) throw new BusinessException("当前访问次数超过上限～～～");
            sum+=1;
            redisTemplate.opsForValue().set(requestUserStr,sum,120,TimeUnit.SECONDS);
        }
        // 1,验证用户的md5 是否存在
        Object mdtObject = redisTemplate.opsForValue().get("userMd5" + goodsId + ":" + userId);
        if (ObjectUtils.isEmpty(mdtObject)) {
            throw new BusinessException(" 验证消息不能为空！");
        }
        if (!md5.equals((String) mdtObject)) throw new BusinessException("非法的验证消息～～");

        //设置个等待时间,如果在 等待的时间内获取到了 token令牌,则处理业务 ，如果在 等待时间内没有获取到响应token则抛弃
        if (!rateLimiter.tryAcquire(5, TimeUnit.SECONDS)) {
            throw new BusinessException(" 抢购太火爆，请稍后重试～～～～");
        }
        //校验商品库存
        Goods goods = goodsService.checkGoodsStock(goodsId);
        //修改销售额度
        if (goodsService.updateGoods(goods) < 1) throw new BusinessException("抢购失败～～");
        // 创建订单
        orderService.createOrder(goods);
        return "购买成功";
    }


    @RequestMapping("/setUserMd5")
    public String generateUserMd5(int goodsId, int userId) {
        String randomMd5Str = "Q.#3!0dq";
        String md5Str = Md5Util.md5("userMd5:" + goodsId + randomMd5Str + userId);
        //存入到redis 60秒的过期时间
        redisTemplate.opsForValue().set("userMd5" + goodsId + ":" + userId, md5Str, 60, TimeUnit.SECONDS);
        return md5Str;
    }

    @RequestMapping("/setGoodsToRedis")
    public String setGoodsToRedis(int goodsId) {
        // 使用定时器，在每天的几点到几点内，将 kill_ID +商品ID 存入到redis，并设置过期时间
        // 现在直接使用接口或者直接编写代码，把商品信息加入redis
        Goods goods = goodsService.getById(goodsId);
        //设置redis 的key和value
        redisTemplate.opsForValue().set("kill_id" + goodsId, goods, 60, TimeUnit.SECONDS);
        return "设置开奖时间成功～～～";
    }
}
