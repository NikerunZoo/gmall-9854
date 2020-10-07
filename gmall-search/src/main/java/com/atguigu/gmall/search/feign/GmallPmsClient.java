package com.atguigu.gmall.search.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author NikerunZoo
 * @date 2020/10/6 0006 9:01
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
