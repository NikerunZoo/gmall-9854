package com.atguigu.gmall.oms.dao;

import com.atguigu.gmall.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author NikerunZoo
 * @email 985423258@qq.com
 * @date 2020-09-24 22:14:17
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
