package com.myself.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myself.seckill.entity.Goods;
import com.myself.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2021-01-23
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();


    GoodsVo findGoodsVoById(Long goodsId);


    int updateByVersion(Goods goods);
}
