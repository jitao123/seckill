package com.myself.seckill.exception;

import com.myself.seckill.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: AT
 * @Date: 2021/1/19 5:21 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends Exception {
    private RespBeanEnum respBeanEnum;
}
