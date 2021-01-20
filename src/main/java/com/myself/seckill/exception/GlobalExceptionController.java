package com.myself.seckill.exception;

import com.myself.seckill.vo.RespBean;
import com.myself.seckill.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @description:
 * @author: AT
 * @Date: 2021/1/19 5:22 下午
 */
@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandler(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException exception = (GlobalException) e;
            return RespBean.error(exception.getRespBeanEnum());

        } else if (e instanceof BindException) {
            BindException exception = (BindException) e;
            RespBean error = RespBean.error(RespBeanEnum.BIND_ERROR);
            error.setMessage(" 参数校验异常： " + exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return error;


        }

        return RespBean.error(RespBeanEnum.ERROR);
    }


}
