package com.myself.seckill.customizecache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author :AT
 * @date : 2022/6/15 10:18 上午
 */
public class MyCustomizeCacheClobal {
    public static ConcurrentHashMap<String,MyCustomizeCache> concurrentHashMap=new ConcurrentHashMap<>();
}
