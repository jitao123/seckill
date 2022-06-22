package com.myself.seckill.myaop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author :AT
 * @date : 2022/6/16 4:26 下午
 */
@Component
@Aspect
public class ControllerAop {

    @Pointcut("execution(* com.myself.seckill.service.impl.OrderServiceImpl.*(..)) ")
    public void myCut()  {

    }

    @Before("myCut()")
    public void before(){
        System.out.println("before--->");
    }

    @After("myCut()")
    public void after(){
        System.out.println("after--->");
    }
}
