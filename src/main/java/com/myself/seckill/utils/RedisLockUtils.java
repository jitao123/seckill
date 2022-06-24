package com.myself.seckill.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @description: 实现redis分布式锁的方法
 * @author :AT
 * @date : 2022/6/24 10:51 上午
 */
@Slf4j
@Component
public class RedisLockUtils {

    public static final String SCRIPT_TEXT = "if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * 加锁
     * @param key 当keyValue 需要替换成其他的数据时可以从threadLocal 中进行存放
     * @param expire
     * @param threadLocal 如果不使用threadName的话，也可以用threadLocal 存放其他的业务参数进行逻辑处理
     * @param stringRedisTemplate
     * @return
     */
    public Boolean lockEnable(String key, long expire,ThreadLocal threadLocal,StringRedisTemplate stringRedisTemplate) {

        //这是将当前线程的名字置为key的value值,表明该锁被谁拿到
        String keyValue = Thread.currentThread().getName();

        //1,这是StringRedisTemplate在set key的同时增加了过期时间，防止死锁。保证了原子性。
        //2,setIfAbsent该方法如果该key不存在时候，设置值进去后，返回true;若是已经存在，则返回false;
        Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent(key, keyValue, expire, TimeUnit.SECONDS);
        Long surplusTime = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (!aBoolean) {
            log.info("该线程 {} 加锁失败,该key {} 剩余过期时间 {} 秒", keyValue, key, surplusTime);
            return false;
        }
        log.info("该线程 {} 加锁成功,该key {} 剩余过期时间 {} 秒", keyValue, key, surplusTime);
        return true;
    }


    /**
     * 解锁 当keyValue 需要替换成其他的数据时可以从threadLocal 中进行获取
     * @param key
     * @param threadLocal
     * @param stringRedisTemplate
     * @return
     */
    public Boolean lockUnable(String key,ThreadLocal threadLocal,StringRedisTemplate stringRedisTemplate) {
        String keyValue = Thread.currentThread().getName();
        //key和value不一致时，返回：【0】
        //key和value一致时，返回：【1】

        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptText(SCRIPT_TEXT);
        Long execute = stringRedisTemplate.execute(defaultRedisScript, Arrays.asList(key, keyValue));
        if(execute != 1 ){
            Boolean aBoolean = stringRedisTemplate.hasKey(key);
            Long surplusTime = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
            log.info("该key {} 解锁失败,是否存在 {},剩余过期时间 {} 秒", key, aBoolean, surplusTime);
            return false;
        }
        log.info("该key {} 解锁成功", key);
        Boolean aBoolean = stringRedisTemplate.hasKey(key);
        log.info("该key是否存在 {} ",aBoolean);
        return true;
    }
}
