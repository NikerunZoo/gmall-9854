package com.atguigu.gmall.auth.feign;

import com.atguigu.gmall.ums.api.GmallUmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author NikerunZoo
 * @date 2020/10/11 0011 16:41
 */
@FeignClient("ums-service")
public interface GmallUmsClient extends GmallUmsApi {
}
