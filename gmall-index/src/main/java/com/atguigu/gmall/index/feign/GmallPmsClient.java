package com.atguigu.gmall.index.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author NikerunZoo
 * @date 2020/10/8 0008 15:39
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
