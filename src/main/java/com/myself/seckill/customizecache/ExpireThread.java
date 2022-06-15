package com.myself.seckill.customizecache;

import java.util.concurrent.TimeUnit;

/**
 * @author :AT
 * @date : 2022/6/15 10:22 上午
 */
public class ExpireThread implements Runnable{
    @Override
    public void run() {
        while (true){
            try {
                TimeUnit.SECONDS.sleep(10);
                expireCache();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void expireCache() {
        System.out.println("检测存储是否过期");
        for (String key : MyCustomizeCacheClobal.concurrentHashMap.keySet()) {
            MyCustomizeCache customizeCache = MyCustomizeCacheClobal.concurrentHashMap.get(key);
            long expireTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - customizeCache.getWriteTime());
            if (customizeCache.getExpireTime()>expireTime) {
                continue;
            }
            //清除过期缓存
            MyCustomizeCacheClobal.concurrentHashMap.remove(key);
        }
    }
}
