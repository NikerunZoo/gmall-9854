package com.atguigu.gmall.wms.vo;

import lombok.Data;

/**
 * @author NikerunZoo
 * @date 2020/10/14 0014 16:15
 */
@Data
public class SkuLockVO {

    private Long skuId;

    private Integer count;

    private Long wareSkuId;//锁定库存的id

    private Boolean lock; //商品的锁定状态

    private String orderToken;
}
