package com.atguigu.gmall.api;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.entity.OrderEntity;
import com.atguigu.gmall.vo.OrderSubmitVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author NikerunZoo
 * @date 2020/10/14 0014 12:06
 */
public interface GmallOmsApi {
    @PostMapping("oms/order")
    public Resp<OrderEntity> saveOrder(@RequestBody OrderSubmitVO submitVO);
}
