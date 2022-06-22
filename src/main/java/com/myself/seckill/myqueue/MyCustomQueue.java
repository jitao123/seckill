package com.myself.seckill.myqueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author :AT
 * @date : 2022/6/15 1:42 下午
 */
public class MyCustomQueue {
    // 自定义的消息队列
    private static Queue<String> queue=new LinkedList<String>();

    public static void main(String[] args) {
       // 生产者
        producer();
        //消费者
        consumer();
    }

    /**
     * 消费者
     */
    public static void consumer() {
        while (!queue.isEmpty()) {
            System.out.println("message --> : "+queue.poll());
        }
    }

    /**
     * 生产者
     */
    public static void producer() {
        queue.add("first");
        queue.add("second");
        queue.add("three");
    }
}
