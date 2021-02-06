package com.myself.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.seckill.entity.Goods;
import com.myself.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2021-01-23
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 通过 goodsId 获取 GoodsVo
     * @param goodsId
     * @return
     */
    GoodsVo findGoodsVoById(Long goodsId);

    /**
     * 通过商品ID 获取 Goods 检测是否超出库存
     * @param goodsId
     * @return
     */
    Goods checkGoodsStock(int goodsId)throws Exception;

    int updateGoods(Goods goods);
}
