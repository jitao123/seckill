package com.myself.seckill.vo;

import com.myself.seckill.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description:
 * @author: AT
 * @Date: 2021/1/23 4:36 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo extends Goods {
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;

    /**
     * 秒杀商品库存
     */
    private Integer stockCount;

    /**
     * 秒杀开始时间
     */
    private Date startDate;

    /**
     * 秒杀结束时间
     */
    private Date endDate;
}
