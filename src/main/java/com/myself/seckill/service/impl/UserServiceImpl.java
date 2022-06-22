package com.myself.seckill.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @Autowired
    private RedisTemplate redisTemplate;

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
//        request.getSession().setAttribute(ticket, user);
//      将用户信息存放在redis 里面

        redisTemplate.opsForValue().set("user:"+ticket , user);
        // 设置cookie
        CookieUtil.setCookie(request, response, "userTicket", ticket);

        return RespBean.success();
    }

    @Override
    public User getUserByCookie(String ticket,HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (StringUtils.isEmpty(ticket)) {
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        if (!ObjectUtils.isEmpty(user)) {
            CookieUtil.setCookie(request, response, "userTicket", ticket);
        }
//        收货地址  delivery_addr_id


        return user;
    }

    /**
     * 分页查询user
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Page<User> queryPage(int pageNo, int pageSize,String userName) {
        Page<User> page = new Page<>(pageNo,pageSize);
        // 根据需求自定义查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(StringUtils.isNotEmpty(userName),User::getNickname,userName);
        List list= this.baseMapper.queryPage(queryWrapper,(pageNo-1)*pageSize,pageSize);
        if (!CollectionUtils.isEmpty(list)) {
            List<User> users = (List<User>) list.get(0);
            List<Integer> integers = (List<Integer>) list.get(1);
            page.setRecords(users);
            page.setTotal(integers.get(0));
        }
        return page;
    }
}
