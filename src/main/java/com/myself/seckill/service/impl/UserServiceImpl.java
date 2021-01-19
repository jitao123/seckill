package com.myself.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myself.seckill.entity.User;
import com.myself.seckill.mapper.UserMapper;
import com.myself.seckill.service.IUserService;
import com.myself.seckill.utils.Md5Util;
import com.myself.seckill.vo.LoginVo;
import com.myself.seckill.vo.RespBean;
import com.myself.seckill.vo.RespBeanEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2021-01-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public RespBean doLogin(@Validated LoginVo loginVo) {
        User user = this.baseMapper.selectById(loginVo.getMobile());
        if (!ObjectUtils.isEmpty(user)) {
            if (!Md5Util.fromPassToDBpass(loginVo.getPassword(),user.getSlat()).equals(loginVo.getPassword())) {
                return  RespBean.error(RespBeanEnum.LOGIN_ERROR);
            }
        }
        return null;
    }
}
