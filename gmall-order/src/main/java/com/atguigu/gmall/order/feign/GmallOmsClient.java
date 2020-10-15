package com.atguigu.gmall.order.feign;

import com.atguigu.gmall.api.GmallOmsApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author NikerunZoo
 * @date 2020/10/14 0014 12:06
 */
@FeignClient("oms-service")
public interface GmallOmsClient extends GmallOmsApi{
}
