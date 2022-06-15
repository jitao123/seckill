package com.myself.seckill.customizecache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :AT
 * @date : 2022/6/15 10:00 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCustomizeCache implements Comparable<MyCustomizeCache>{

    /**
     * key
     */
    private Object key;
    /**
     * Value
     */
    private Object value;
    /**
     * 上次访问时间
     */
    private long lastTime;
    /**
     * 创建时间
     */
    private long writeTime;
    /**
     *存活时间
     */
    private long expireTime;
    /**
     * 命中次数
     */
    private Integer hitCount;


    @Override
    public int compareTo(MyCustomizeCache myCustomizeCache) {
        return hitCount.compareTo(myCustomizeCache.hitCount);
    }
}
