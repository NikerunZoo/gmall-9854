package com.atguigu.gmall.oms.dao;

import com.atguigu.gmall.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author NikerunZoo
 * @email 985423258@qq.com
 * @date 2020-09-24 22:14:18
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    public int closeOrder(String orderToken);

    public int payOrder(String orderToken);
}
