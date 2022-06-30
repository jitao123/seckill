package com.myself.seckill.myqueue;

import cn.hutool.core.util.StrUtil;
import redis.clients.jedis.Jedis;

/**
 * @author :AT
 * @date : 2022/6/27 10:45 上午
 */
public class RedisListMq {
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> bConsumer()).start();
        producer();
    }

    /**
     *一直读取，数据为空时 浪费资源
     */
    public static void consumer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        while (true) {
            String myMq = jedis.rpop("test");
            if (StrUtil.isNotEmpty(myMq)) {
                System.out.println("test = " + myMq);
            }
        }
    }

    /**
     * 阻塞读取
     */
    public static void bConsumer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        while (true) {
            for (String mq : jedis.brpop(0, "test")) {
                if (StrUtil.isNotEmpty(mq)) {
                    //会打印队列名称
                    //mq => test
                    //mq => hello word!
                    //mq => test
                    //mq => hello java!
                    //mq => test
                    //mq => hello python!
                    System.out.println("mq => " + mq);
                }
            }
        }
    }

    public static void producer() throws InterruptedException {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        Thread.sleep(1000);
        jedis.rpush("test","hello word!");
        Thread.sleep(2000);
        jedis.rpush("test","hello java!");
        Thread.sleep(3000);
        jedis.rpush("test","hello python!");
    }
}
