package com.myself.seckill.myqueue;

import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author :AT
 * @date : 2022/6/15 1:51 下午
 */
public class MyCustomDelayQueue {
    // 自定义延迟时间队列
    public static DelayQueue queue=new DelayQueue();

    public static void main(String[] args) throws InterruptedException {
        //生产者
        producer();
        //消费者
        consumer();
    }

    /**
     * 消费者
     */
    private static void consumer() throws InterruptedException {
        System.out.println(DateUtil.now());
        while (!queue.isEmpty()) {
            // 强制转换成自定义的实现了Delay接口的类
            MyDelay take = (MyDelay) queue.take();
            System.out.println("----> : "+  take.getMsg());
        }
        System.out.println(DateUtil.now());
    }

    /**
     * 生产者
     */
    private static void producer() {
        queue.put(new MyDelay(1000,"测试消息1"));
        queue.put(new MyDelay(3000,"测试消息2"));
    }

    static class MyDelay implements Delayed{

        public long delayTime=System.currentTimeMillis();

        @Getter
        @Setter
        private String msg;

        public MyDelay(long delayTime, String msg) {
            this.delayTime = (this.delayTime+delayTime);
            this.msg = msg;
        }

        @Override
        public long getDelay(TimeUnit timeUnit) {
            return timeUnit.convert(delayTime-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed delayed) {
            if (this.getDelay(TimeUnit.MILLISECONDS)> delayed.getDelay(TimeUnit.MILLISECONDS)) {
                return 1;
            }else if (this.getDelay(TimeUnit.MILLISECONDS)<delayed.getDelay(TimeUnit.MILLISECONDS)){
                return -1;
            }
            return 0;
        }
    }
}
