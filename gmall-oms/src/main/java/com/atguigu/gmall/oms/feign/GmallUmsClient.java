package com.atguigu.gmall.oms.feign;

import com.atguigu.gmall.ums.api.GmallUmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author NikerunZoo
 * @date 2020/10/15 0015 9:55
 */
@FeignClient("ums-service")
public interface GmallUmsClient extends GmallUmsApi {
}
