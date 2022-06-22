package com.myself.seckill.myaop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author :AT
 * @date : 2022/6/17 10:15 上午
 */
@Aspect
@Component
public class MyLogAopAspect {
    //注解的方式 @annotation 表达式 , 方法或者类用 execution 表达式
    @Pointcut("@annotation(com.myself.seckill.myaop.MyLogAop)")
    public void MyPointCut(){

    }

    @Before("MyPointCut()")
    public void MyBefore(){
        System.out.println("MyBefore---> MyPointCut--->");
    }

    @After("MyPointCut()")
    public void MyAfter(){
        System.out.println("MyAfter---> MyPointCut--->");
    }
}
