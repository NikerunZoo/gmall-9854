package com.atguigu.gmall.cart.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author NikerunZoo
 * @date 2020/10/9 0009 18:49
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
