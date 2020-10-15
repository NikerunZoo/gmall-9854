package com.atguigu.gmall.order.pay;

import lombok.Data;

/**
 * @author NikerunZoo
 * @date 2020/10/15 0015 16:43
 */
@Data
public class PayVo {
    private String out_trade_no; // 商户订单号 必填
    private String subject; // 订单名称 必填
    private String total_amount;  // 付款金额 必填
    private String body; // 商品描述 可空
}