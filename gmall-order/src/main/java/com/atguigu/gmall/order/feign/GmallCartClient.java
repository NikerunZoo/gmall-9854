package com.atguigu.gmall.order.feign;

import com.atguigu.gmall.cart.api.GmallCartApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author NikerunZoo
 * @date 2020/10/14 0014 12:04
 */
@FeignClient("cart-service")
public interface GmallCartClient extends GmallCartApi {
}
