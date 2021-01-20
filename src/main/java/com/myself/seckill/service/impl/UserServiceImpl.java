package com.myself.seckill.service.impl;

import cn.hutool.core.lang.generator.UUIDGenerator;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myself.seckill.entity.User;
import com.myself.seckill.exception.GlobalException;
import com.myself.seckill.mapper.UserMapper;
import com.myself.seckill.service.IUserService;
import com.myself.seckill.utils.CookieUtil;
import com.myself.seckill.utils.Md5Util;
import com.myself.seckill.vo.LoginVo;
import com.myself.seckill.vo.RespBean;
import com.myself.seckill.vo.RespBeanEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2021-01-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.baseMapper.selectById(loginVo.getMobile());
        if (ObjectUtils.isEmpty(user)) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        if (!Md5Util.fromPassToDBpass(loginVo.getPassword(), user.getSlat()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
//        设置 uuid
        String ticket = IdUtil.simpleUUID();
        request.getSession().setAttribute(ticket, user);
        // 设置cookie
        CookieUtil.setCookie(request, response, "userTicket", ticket);


        return RespBean.success();
    }
}
