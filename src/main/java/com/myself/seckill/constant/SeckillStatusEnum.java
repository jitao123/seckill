package com.myself.seckill.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: AT
 * @Date: 2021/1/25 1:41 下午
 */
@Getter
@AllArgsConstructor
public enum SeckillStatusEnum {


     NO_DRAW(0,"未开奖"),
      SECKILL(1,"秒杀中"),
     OVER(2,"已结束");


    private int status;
    private String description;

}
