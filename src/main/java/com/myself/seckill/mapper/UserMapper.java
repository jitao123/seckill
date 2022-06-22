package com.myself.seckill.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.myself.seckill.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2021-01-19
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询list
     * @param pageNo
     * @param pageSize
     * @return
     */
    List queryPage(@Param(Constants.WRAPPER) QueryWrapper<User> queryWrapper,@Param("pageNo")int pageNo,@Param("pageSize")int pageSize);
}
