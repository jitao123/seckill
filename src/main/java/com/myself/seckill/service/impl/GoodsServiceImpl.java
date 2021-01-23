package com.myself.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myself.seckill.entity.Goods;
import com.myself.seckill.mapper.GoodsMapper;
import com.myself.seckill.service.IGoodsService;
import com.myself.seckill.vo.GoodsVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2021-01-23
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    /**
     * 获取商品列表
     *
     * @return
     */
    @Override
    public List<GoodsVo> findGoodsVo() {
        return this.baseMapper.findGoodsVo();
    }
}
