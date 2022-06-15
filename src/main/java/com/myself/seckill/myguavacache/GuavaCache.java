package com.myself.seckill.myguavacache;

import com.google.common.cache.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author :AT
 * @date : 2022/6/14 5:22 下午
 */
public class GuavaCache {
    public static void main(String[] args) throws ExecutionException {
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                //并发级别设置为5
                .concurrencyLevel(5)
                // 设置过期时间为8秒钟
                .expireAfterAccess(8, TimeUnit.SECONDS)
                // 初始化的容量长度为10
                .initialCapacity(10)
                // 最大的容量长度为100
                .maximumSize(100)
                //设置统计缓存的命中率
                .recordStats()
                //设置缓存的移除通知
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> removalNotification) {
                        System.out.println(removalNotification.getKey() + " key was remove , cache is   " + removalNotification.getValue());
                    }
                })
                //指定CacheLoader 缓存不存在时 ，可自动加载缓存
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        // 自动加载缓存业务
                        return "不存在的key : " + key;
                    }
                });
        String testGuava = "testGuava";
        loadingCache.put(testGuava,"value1");
        String resultValue = loadingCache.get(testGuava);
        System.out.println("resultValue = " + resultValue);
         resultValue = loadingCache.get(testGuava+"1");
        System.out.println("resultValue2 = " + resultValue);


        //第二种方式
        Cache<String, String> objectCache = CacheBuilder.newBuilder().maximumSize(100).build();

        objectCache.put(testGuava,"max100");
        // 取一个存在的值
        String result = objectCache.get(testGuava, new Callable<String>() {
            @Override
            public String call() throws Exception {
                //自定义返回值
                return "null";
            }
        });
        System.out.println("result = " + result);
        result = objectCache.get(testGuava+"1", new Callable<String>() {
            @Override
            public String call() throws Exception {
                //自定义返回值
                return "不存在～～～～ 自定义";
            }
        });
        System.out.println("result = " + result);
    }
}
