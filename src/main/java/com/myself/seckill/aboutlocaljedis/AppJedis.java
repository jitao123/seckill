package com.myself.seckill.aboutlocaljedis;

import cn.hutool.core.util.StrUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 用模拟redis 并发秒杀
 * @author :AT
 * @date : 2022/6/13 2:23 下午
 */
public class AppJedis {

    /**
     * 自定义一个线程池
     */
    public static ThreadPoolExecutor poolExecutor=new ThreadPoolExecutor(10,
            100,10,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>());

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost",6379);
        //默认选择第一个数据库
        jedis.select(1);
        // 设置总数 50个
        jedis.set("kill_num","50");
        // 清空抢购用户列表
        jedis.del("kill_list");
        jedis.close();

        for (int i = 0; i < 1000; i++) {
            // 执行1000次 ，模拟1000次并发
            poolExecutor.execute(new Mykill());
        }

    }
}
