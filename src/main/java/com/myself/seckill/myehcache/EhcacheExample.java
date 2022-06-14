package com.myself.seckill.myehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.EhcacheManager;

/**
 * @author :AT
 * @date : 2022/6/14 3:37 下午
 */
public class EhcacheExample {
    public static void main(String[] args) {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();

        Cache<String, String> myCeche = cacheManager.createCache("MYCECHE", CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class, String.class, ResourcePoolsBuilder.heap(10)));


        Cache<String, String> myCeche2 = cacheManager.createCache("MYCECHE2", CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class, String.class, ResourcePoolsBuilder.heap(10)));

        myCeche.put("testKey","value1");
        myCeche2.put("testKey","value2");
        String testKey = myCeche.get("testKey");
        String testKey2 = myCeche2.get("testKey");
        System.out.println("testKey = " + testKey);
        System.out.println("testKey2 = " + testKey2);
        //testKey = value1
        //testKey2 = value2
        cacheManager.close();
        // CacheManager 下 可以有多个 Cache ，每个Cache 下可以有多个 k-v
    }
}
