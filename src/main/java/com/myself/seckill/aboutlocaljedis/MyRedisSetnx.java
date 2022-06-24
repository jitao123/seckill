package com.myself.seckill.aboutlocaljedis;

import cn.hutool.core.util.IdUtil;
import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * @author :AT
 * @date : 2022/6/23 3:51 下午
 */
public class MyRedisSetnx {

    //    解锁原子性操作脚本
    public  static   String script = "if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end ";

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        //默认选择第一个数据库
        jedis.select(1);

        for (int i = 0; i < 2; i++) {
            String key = String.valueOf(i);
            String flag = IdUtil.simpleUUID();
            boolean result = result(jedis, key, flag);
            System.out.println("result => " + result);
        }


    }

    public static boolean result(Jedis jedis, String key, String flag) {
        Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(flag));
        if ("1L".equals(result)) {
            return true;
        }
        return false;
    }
}
