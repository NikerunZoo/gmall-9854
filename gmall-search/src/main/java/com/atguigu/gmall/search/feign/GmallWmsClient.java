package com.atguigu.gmall.search.feign;

import com.atguigu.gmall.wms.api.GmallWmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author NikerunZoo
 * @date 2020/10/6 0006 9:02
 */
@FeignClient("wms-service")
public interface GmallWmsClient extends GmallWmsApi {
}
