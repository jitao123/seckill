package com.myself.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: AT
 * @Date: 2021/1/19 3:02 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespBean {

    private long code;
    private String message;
    private Object object;

    /**
     * 成功
     *
     * @return
     */
    public static RespBean success() {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMsg(), null);
    }

    public static RespBean success(Object object) {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMsg(), object);
    }


    public static RespBean error() {
        return new RespBean(RespBeanEnum.ERROR.getCode(), RespBeanEnum.ERROR.getMsg(), null);
    }


    public static RespBean error(RespBeanEnum respBeanEnum) {
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMsg(), null);
    }


}
