package com.myself.seckill.customizecache;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.myself.seckill.customizecache.MyCustomizeCacheClobal.concurrentHashMap;

/**
 * @author :AT
 * @date : 2022/6/15 10:49 上午
 */
public class MyCacheUtil {

    /**
     * 存放key、value
     * @param key
     * @param value
     * @param expireTime
     */
    public static void put(String key, Object value, long expireTime) {

        if (StrUtil.isEmpty(key)) {
            return;
        }
        // 判断在缓存中是否存在key
        if (concurrentHashMap.containsKey(key)) {
            MyCustomizeCache customizeCache = concurrentHashMap.get(key);
            customizeCache.setValue(value);
            customizeCache.setLastTime(System.currentTimeMillis());
            customizeCache.setWriteTime(System.currentTimeMillis());
            customizeCache.setExpireTime(expireTime);
            customizeCache.setHitCount(customizeCache.getHitCount() + 1);
            return;
        }
        MyCustomizeCache customizeCache = new MyCustomizeCache();
        customizeCache.setKey(key);
        customizeCache.setValue(value);
        customizeCache.setLastTime(System.currentTimeMillis());
        customizeCache.setWriteTime(System.currentTimeMillis());
        customizeCache.setExpireTime(expireTime);
        customizeCache.setHitCount(1);
        concurrentHashMap.put(key, customizeCache);
    }


    /**
     * 根据key返回数据
     * @param key
     * @return
     */
    public static Object get(String key){
        if (StrUtil.isEmpty(key)) {
            return null;
        }
        if (ObjectUtils.isEmpty(concurrentHashMap)) {
            return null;
        }
        if (!concurrentHashMap.containsKey(key)) {
            return null;
        }
        MyCustomizeCache myCustomizeCache = concurrentHashMap.get(key);
        if (Objects.isNull(myCustomizeCache)) {
            return null;
        }
        // 惰性删除
        long duration = System.currentTimeMillis() - myCustomizeCache.getWriteTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        // 过期时间小于 当前时间-写入时间
        if (myCustomizeCache.getExpireTime()<=seconds) {
            concurrentHashMap.remove(key);
            return null;
        }
        myCustomizeCache.setHitCount(myCustomizeCache.getHitCount()+1);
        myCustomizeCache.setLastTime(System.currentTimeMillis());
        return myCustomizeCache.getValue();
    }
}
