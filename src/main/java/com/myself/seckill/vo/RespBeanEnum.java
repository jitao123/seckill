package com.myself.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @description:
 * @author: AT
 * @Date: 2021/1/19 3:02 下午
 */
@Getter
@AllArgsConstructor
@ToString
public enum RespBeanEnum {

    //    通用
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务器异常"),

    //    通用
    LOGIN_ERROR(500210, "用户名或者密码不能为空"),

    MOBILE_ERROR(500211,"手机号不能为空或者格式不正确");

    private int code;
    private String msg;
}
