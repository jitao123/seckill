package com.myself.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myself.seckill.entity.Goods;
import com.myself.seckill.exception.BusinessException;
import com.myself.seckill.mapper.GoodsMapper;
import com.myself.seckill.service.IGoodsService;
import com.myself.seckill.vo.GoodsVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
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

    /**
     * 通过 goodsId 获取 GoodsVo
     *
     * @param goodsId
     * @return
     */
    @Override
    public GoodsVo findGoodsVoById(Long goodsId) {
        return this.baseMapper.findGoodsVoById(goodsId);
    }

    /**
     * 通过商品ID 获取 Goods 检测是否超出库存
     *
     * @param goodsId
     * @return
     */
    @Override
    public Goods checkGoodsStock(int goodsId)throws Exception {
        Goods goods = this.getById(goodsId);
        if (ObjectUtils.isEmpty(goods) || (goods.getSalesNumber() >= goods.getGoodsStock())) {
            throw new BusinessException("抢购失败～～");
        }
        return goods;
    }

    @Override
    public int updateGoods(Goods goods) {

        return this.baseMapper.updateByVersion(goods);
    }
}
