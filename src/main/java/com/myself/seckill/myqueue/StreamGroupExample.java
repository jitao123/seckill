package com.myself.seckill.myqueue;

import cn.hutool.json.JSONUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntry;
import redis.clients.jedis.StreamEntryID;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :AT
 * @date : 2022/6/27 11:09 上午
 */
public class StreamGroupExample {
    public static final String STREAM_KEY="myMq";
    public static final String GROUP_NAME="g1   ";
    public static final String CONSUMER_NAME="c1";
    public static final String CONSUMER2_NAME="c2";


    public static void main(String[] args) {
        //生产者
        producer();
        // 创建分组
        createGroup(STREAM_KEY,GROUP_NAME);
        // 消费者1
        new Thread(()->consumer1()).start();
        // 消费者2
        new Thread(()->consumer2()).start();
    }

    private static void consumer2() {
        Jedis jedis = new Jedis("127.0.0.1", 6379,100000);
        while (true) {
            Map.Entry<String, StreamEntryID> entry = new AbstractMap.SimpleImmutableEntry<>(STREAM_KEY,

                    StreamEntryID.UNRECEIVED_ENTRY);

            // 阻塞读取一条消息（最大阻塞时间120s）
            List<Map.Entry<String, List<StreamEntry>>> list = jedis.xreadGroup(GROUP_NAME, CONSUMER2_NAME, 1,

                    120 * 1000, true, entry);

            if (list!=null && list.size()==1) {
                // 读取到消息
                Map<String, String> content = list.get(0).getValue().get(0).getFields(); // 消息内容

                System.out.println("Consumer 2 读取到消息 ID：" + list.get(0).getValue().get(0).getID() +

                        " 内容：" + JSONUtil.toJsonStr(content));
            }
        }
    }

    private static void consumer1() {
        Jedis jedis = new Jedis("127.0.0.1", 6379,100000);
        while (true) {
            Map.Entry<String, StreamEntryID> entry = new AbstractMap.SimpleImmutableEntry<>(STREAM_KEY,

                     StreamEntryID.UNRECEIVED_ENTRY);

            // 阻塞读取一条消息（最大阻塞时间120s）
            Map<String, StreamEntryID> map = new HashMap<>();
            map.put(entry.getKey(),entry.getValue());

            // 阻塞读取一条消息（最大阻塞时间120s）
            List<Map.Entry<String, List<StreamEntry>>> list = jedis.xreadGroup(GROUP_NAME, CONSUMER_NAME, 1,

                    120 * 1000, true, entry);
            if (list!=null && list.size()==1) {
                // 读取到消息
                Map<String, String> content = list.get(0).getValue().get(0).getFields(); // 消息内容

                System.out.println("Consumer 1 读取到消息 ID：" + list.get(0).getValue().get(0).getID() +

                        " 内容：" + JSONUtil.toJsonStr(content));
            }
        }
    }

    /**
     * 创建分组
     * @param streamKey
     * @param groupName
     */
    private static void createGroup(String streamKey, String groupName) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.xgroupCreate(streamKey,groupName,new StreamEntryID(),true);
    }

    private static void producer() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        Map<String, String> map = new HashMap<>();
        map.put("data","redis");

        StreamEntryID streamEntryID = jedis.xadd(STREAM_KEY,  null, map);
        System.out.println("添加id成功 => " + streamEntryID);
        map.put("data","java");
        StreamEntryID streamEntryID2 = jedis.xadd(STREAM_KEY,  null, map);
        System.out.println("添加id成功 => " + streamEntryID2);
    }

    public static  String result(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("10");
        return buffer.toString();
    }
    // 关于xGroup的命令
    //127.0.0.1:6379> XRANGE "myMq" - + // 显示所有的值
    //1) 1) "1656316669078-0"
    //   2) 1) "data"
    //      2) "redis"
    //2) 1) "1656316669080-0"
    //   2) 1) "data"
    //      2) "java"
    //127.0.0.1:6379> XDEL "myMq" "1656316669078-0" "1656316669080-0" // 根据key 和分组ID删除对应的值
    //(integer) 2
    //127.0.0.1:6379> del "myMq" // 删除key
}
