package com.atguigu.gmall.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author NikerunZoo
 * @date 2020/10/14 0014 15:20
 */
@Configuration
public class ThreadPollConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
        return new ThreadPoolExecutor(50,200,60, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10000));
    }
}
