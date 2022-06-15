package com.myself.seckill.customizecache;

/**
 * @author :AT
 * @date : 2022/6/15 11:06 上午
 */
public class MyCacheTest {
    public static void main(String[] args) {
        String key = "test1";
        MyCacheUtil.put(key,2342134,10);
        Object test1 = MyCacheUtil.get(key);
        System.out.println("test1 = " + test1);
        Object test2 = MyCacheUtil.get(key+1);
        System.out.println("test2 = " + test2);

        // 打印结果
        //test1 = 2342134
        //test2 = null
    }
}
