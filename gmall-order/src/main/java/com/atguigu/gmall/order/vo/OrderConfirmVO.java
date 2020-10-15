package com.atguigu.gmall.order.vo;

import com.atguigu.gmall.ums.entity.MemberReceiveAddressEntity;
import com.atguigu.gmall.vo.OrderItemVO;
import lombok.Data;

import java.util.List;

/**
 * @author NikerunZoo
 * @date 2020/10/14 0014 11:37
 */
@Data
public class OrderConfirmVO {

    private List<MemberReceiveAddressEntity> addresses;

    private List<OrderItemVO> orderItems;

    private Integer bounds;

    private String orderToken;  //防止订单重复提交
}
