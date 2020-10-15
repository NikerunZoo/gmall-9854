package com.atguigu.gmall.order.feign;

import com.atguigu.gmall.ums.api.GmallUmsApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author NikerunZoo
 * @date 2020/10/14 0014 12:03
 */
@FeignClient("ums-service")
public interface GmallUmsClient extends GmallUmsApi {
}
