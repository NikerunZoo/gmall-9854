package com.atguigu.gmall.oms.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author NikerunZoo
 * @date 2020/10/15 0015 10:14
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
