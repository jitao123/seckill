package com.myself.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myself.seckill.entity.Goods;
import com.myself.seckill.entity.Order;
import com.myself.seckill.mapper.OrderMapper;
import com.myself.seckill.service.IOrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2021-01-23
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    /**
     * 创建订单
     *
     * @param goods
     */
    @Override
    public void createOrder(Goods goods) {
        Order order = new Order();
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(goods.getGoodsPrice());
        order.setOrderChannel(1);
        order.setStatus(1);
        order.setCreateDate(LocalDateTime.now());
        order.setPayDate(LocalDateTime.now());
        this.baseMapper.insert(order);
    }
}
