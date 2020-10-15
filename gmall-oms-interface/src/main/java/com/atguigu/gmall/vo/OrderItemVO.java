package com.atguigu.gmall.vo;

import com.atguigu.gmall.pms.entity.SkuSaleAttrValueEntity;
import com.atguigu.gmall.sms.vo.SaleVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author NikerunZoo
 * @date 2020/10/14 0014 11:44
 */
@Data
public class OrderItemVO {

    private Long skuId;
    private String title;
    private String defaultImage;
    private BigDecimal price; //数据库实时价格
    private Integer count;  //是否有货
    private Boolean store;
    private List<SkuSaleAttrValueEntity> saleAttrValues;
    private List<SaleVO> sales;
    private BigDecimal weight;
}
