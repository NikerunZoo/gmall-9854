package com.atguigu.gmall.pms.feign;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.sms.api.GmallSmsApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author NikerunZoo
 * @date 2020/9/27 0027 17:08
 */
@FeignClient("sms-service")
public interface GmallSmsClient extends GmallSmsApi {


}
