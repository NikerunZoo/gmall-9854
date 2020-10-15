package com.atguigu.gmall.wms.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author NikerunZoo
 * @date 2020/10/15 0015 14:05
 */

@Configuration
public class RabbitMqConfig {

    @Bean("WMS-TTL-QUEUE")
    public Queue ttlQueue(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange","GMALL-ORDER-EXCHANGE");
        map.put("x-dead-letter-routing-key","stock.unlock");
        map.put("x-message-ttl",350000);
        return new Queue("WMS-TTL-QUEUE",true,false, false,map);
    }

    @Bean("WMS-TTL-BINDING")
    public Binding ttlBinding(){

        return new Binding("WMS-TTL-QUEUE", Binding.DestinationType.QUEUE,"GMALL-ORDER-EXCHANGE","stock.ttl",null);
    }



}
