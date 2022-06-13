package com.myself.seckill.aboutlocaljedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @author :AT
 * @date : 2022/6/13 3:14 下午
 */
public class Mykill implements  Runnable{
    @Override
    public void run() {
        Jedis jedis = new Jedis("localhost", 6379);
        //默认选择第一个数据库
        jedis.select(1);
        // 获取两个key数据的版本号 当提交事物的时候，必须判断两个数据的版本号相等，才允许提交
        // redis 里面已经写好这个逻辑
        jedis.watch("kill_num", "kill_list");
        int num = Integer.parseInt(jedis.get("kill_num"));
        if (num > 0) {
            // 开启redis 事物
            Transaction transaction = jedis.multi();
            // 只减1
            transaction.decr("kill_num");
            // 往key 里面推送数据
            transaction.rpush("kill_list", "test" + Thread.currentThread().getName());
            // 提交事物
            transaction.exec();
        } else {
            // 如果剩余数量已经为0的情况下， 需要关闭线程池
            AppJedis.poolExecutor.shutdown();
        }
        // 每个线程最后都关闭 jedis链接
        jedis.close();
    }
}
