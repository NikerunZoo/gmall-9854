package com.atguigu.gmall.order.feign;

import com.atguigu.gmall.wms.api.GmallWmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author NikerunZoo
 * @date 2020/10/9 0009 18:49
 */
@FeignClient("wms-service")
public interface GmallWmsClient extends GmallWmsApi {
}
