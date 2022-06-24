package com.myself.seckill;

import cn.hutool.core.util.IdUtil;
import com.myself.seckill.entity.User;
import com.myself.seckill.service.IGoodsService;
import com.myself.seckill.service.IOrderService;
import com.myself.seckill.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class SeckillDemoApplicationTests {

    public static final String MY_KEY = "my_key";
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public DefaultRedisScript<Long> defaultRedisScript() {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptText("if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end");
        return defaultRedisScript;
    }
//
//    @Autowired
//    private DefaultRedisScript<Long> defaultRedisScript;

    @Test
    @SuppressWarnings("all")
    void contextLoads() {
//        boolean mobile = ValidatorUtil.isMobile("16608059740");
//        System.out.println("～～～～～～～～～～～"+mobile);
//
//        Goods goods = goodsService.getById(1);
//        int updateGoods = goodsService.updateGoods(goods);
//        orderService.createOrder(goods);
//        System.out.println("updateGoods ～～～～～ : "+updateGoods);

//        FactoryBean factoryBean = (FactoryBean) context.getBean("factoryBean");
//        Class objectType = factoryBean.getObjectType();
//        System.out.println(objectType);
//        Object userService = applicationContext.getBean("redisTemplate");
//        System.out.println(userService.toString());
//        ArrayList list = new ArrayList<String>();
//        ArrayList list2 = new ArrayList<String>(11);
//        System.out.println("list.size() = " + list.size());
//        System.out.println("list2.size() = " + list2.size());
//        CopyOnWriteArrayList cpList= new CopyOnWriteArrayList<String>();
//        cpList.add("sdf");
//        cpList.remove("sdf");
//        cpList.remove(1);

        for (int i = 0; i <1000; i++) {
//            Thread thread = new Thread(() -> {
//
////                threadTest();
//            });
            Thread thread1 = new Thread(new Runnable() {
                public  ThreadLocal<String> threadLocal=new ThreadLocal<String>();
                @Override
                public void run() {
                    threadTest(threadLocal);
                }
            });
//            thread.start();
//            thread.setName("thread" + i);
            thread1.start();
            thread1.setName("thread" + i);
        }
        // 让线程不结束，方便查看控制台日志，否则直接执行完毕没有日志查看
        while (true){

        }
    }


    public void threadTest(ThreadLocal threadLocal) {

        /**
         * 对某一条数据的id进行加锁
         */
        Boolean aBoolean = lockEnable(MY_KEY, 200, threadLocal);


        if (!aBoolean) {
            log.info(String.format("线程 {} 没有拿到锁，结束流程",Thread.currentThread().getName()));
            return;
        }

        User user = new User();
        user.setHand(Thread.currentThread().getName());
        user.setId(497009);
        boolean update = userService.updateById(user);
        if (!update) {
            log.info("线程 {} 更新数据失败",Thread.currentThread().getName());
            return;
        }
        log.info("线程 {} 更新数据成功",Thread.currentThread().getName());

        /**
         * 释放该条数据的锁
         */
        Boolean aBoolean1 = lockUnable(MY_KEY,threadLocal);
        log.info("线程 {} 是否成功释放锁：{}",Thread.currentThread().getName(),aBoolean1);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ee");
        System.out.println("buffer.toString() = " + stringBuffer);
    }

    public Boolean lockEnable(String key, long expire,ThreadLocal threadLocal) {

        //这是将当前线程的名字置为key的value值,表明该锁被谁拿到
//        String keyValue = Thread.currentThread().getName();
        String keyValue = IdUtil.simpleUUID();
        threadLocal.set(keyValue);


        //1,这是StringRedisTemplate在set key的同时增加了过期时间，防止死锁。保证了原子性。
        //2,setIfAbsent该方法如果该key不存在时候，设置值进去后，返回true;若是已经存在，则返回false;
        Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent(key, keyValue, expire, TimeUnit.SECONDS);
        Long surplusTime = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (!aBoolean) {
            log.info("该线程 {} 加锁失败,该key {} 剩余过期时间 {} 秒", keyValue, key, surplusTime);
            return false;
        }
        log.info("该线程 {} 加锁成功,该key {} 剩余过期时间 {} 秒", keyValue, key, surplusTime);
        return true;
    }


    public Boolean lockUnable(String key,ThreadLocal threadLocal) {
//        String keyValue = Thread.currentThread().getName();
        String keyValue = threadLocal.get().toString();
        //key和value不一致时，返回：【0】
        //key和value一致时，返回：【1】

        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptText("if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end");
        Long execute = (Long) stringRedisTemplate.execute(defaultRedisScript, Arrays.asList(key, keyValue));
        if(execute != 1 ){
            Boolean aBoolean = stringRedisTemplate.hasKey(key);
            Long surplusTime = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
            log.info("该key {} 解锁失败,是否存在 {},剩余过期时间 {} 秒", key, aBoolean, surplusTime);
            return false;
        }
        log.info("该key {} 解锁成功", key);
        Boolean aBoolean = stringRedisTemplate.hasKey(key);
        log.info("该key是否存在 {} ",aBoolean);
        return true;
    }


}
