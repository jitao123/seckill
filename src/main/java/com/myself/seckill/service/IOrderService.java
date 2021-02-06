package com.myself.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.seckill.entity.Goods;
import com.myself.seckill.entity.Order;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2021-01-23
 */
public interface IOrderService extends IService<Order> {

    /**
     * 创建订单
     * @param goods
     */
    void createOrder(Goods goods);
}
