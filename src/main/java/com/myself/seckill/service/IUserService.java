package com.myself.seckill.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.seckill.entity.User;
import com.myself.seckill.vo.LoginVo;
import com.myself.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2021-01-19
 */
public interface IUserService extends IService<User> {

    RespBean doLogin(LoginVo loginVo, HttpServletRequest request,HttpServletResponse response)throws Exception ;


    User getUserByCookie(String ticket,HttpServletRequest request,HttpServletResponse response) throws Exception;
}
