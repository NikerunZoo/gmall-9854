package com.atguigu.gmall.vo;

import com.atguigu.gmall.ums.entity.MemberReceiveAddressEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author NikerunZoo
 * @date 2020/10/14 0014 15:54
 */
@Data
public class OrderSubmitVO {

    private  String orderToken;    //防止重复提交
    private MemberReceiveAddressEntity address;
    private Integer payType;
    private  String deliveryCompany;
    private List<OrderItemVO> items;
    private Integer bounds;
    private BigDecimal totalPrice; //校验价格
    private Long userId;
}
