package com.atguigu.gmall.sms.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author NikerunZoo
 * @date 2020/9/27 0027 16:49
 */
@Data
public class SkuSaleVo {

    private Long skuId;

    //几分营销相关字段
    private BigDecimal growBounds;
    private BigDecimal buyBounds;
    private List<Integer> work;

    //打折相关的字段

    private Integer fullCount;
    private BigDecimal discount;
    private Integer ladderAddOther;

    //满减相关的相关字段

    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private Integer fullAddOther;
}
