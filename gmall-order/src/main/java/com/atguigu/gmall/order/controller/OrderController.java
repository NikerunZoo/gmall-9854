package com.atguigu.gmall.order.controller;

import com.alipay.api.AlipayApiException;
import com.atguigu.core.bean.Resp;
import com.atguigu.core.bean.UserInfo;
import com.atguigu.gmall.entity.OrderEntity;
import com.atguigu.gmall.order.interceptors.LoginInterceptor;
import com.atguigu.gmall.order.pay.AlipayTemplate;
import com.atguigu.gmall.order.pay.PayAsyncVo;
import com.atguigu.gmall.order.pay.PayVo;
import com.atguigu.gmall.order.service.OrderService;
import com.atguigu.gmall.order.vo.OrderConfirmVO;
import com.atguigu.gmall.vo.OrderSubmitVO;
import com.atguigu.gmall.wms.vo.SkuLockVO;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author NikerunZoo
 * @date 2020/10/14 0014 11:47
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private AlipayTemplate alipayTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;


    @GetMapping("confirm")
    public Resp<OrderConfirmVO> confirm(){

        OrderConfirmVO confirmVO =this.orderService.confirm();

        return Resp.ok(confirmVO);
    }


    @PostMapping("submit")
    public Resp<Object> submit(@RequestBody OrderSubmitVO submitVO){
        OrderEntity orderEntity = this.orderService.submit(submitVO);

        try {
            PayVo payVo = new PayVo();
            payVo.setOut_trade_no(orderEntity.getOrderSn());
            payVo.setTotal_amount("100");
            payVo.setSubject("it's so  hard");
            payVo.setBody("my first springcloud project");
            String form = this.alipayTemplate.pay(payVo);
            System.out.println(form);
        }catch (AlipayApiException e){
                e.printStackTrace();
        }


        return Resp.ok(null);
    }
    @PostMapping("pay/success")
    public Resp<Object> paySuccess(PayAsyncVo payAsyncVo){


        this.amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE","order.pay",payAsyncVo.getOut_trade_no());

        return Resp.ok(null);

    }


    @PostMapping("seckill/{skuId}")
    public Resp<Object> seckill(@PathVariable("skuId")Long skuId){


        RSemaphore semaphore = this.redissonClient.getSemaphore("semphorelock:" + skuId);
        semaphore.trySetPermits(500);

        if(semaphore.tryAcquire());{

            //获取redis中的库存信息
            String countString = this.redisTemplate.opsForValue().get("order:seckill:" + skuId);

            //没有，秒杀结束
            if (StringUtils.isEmpty(countString) || Integer.parseInt(countString) == 0){
                return   Resp.ok("秒杀结束");
            }
            int count = Integer.parseInt(countString);


            //减库存
            this.redisTemplate.opsForValue().set("order:seckill:" + skuId,String.valueOf(--count));

            //发送消息给消息队列,将来真正的减库存
            SkuLockVO skuLockVO = new SkuLockVO();
            skuLockVO.setCount(1);
            skuLockVO.setSkuId(skuId);
            String orderToken = IdWorker.getIdStr();
            skuLockVO.setOrderToken(orderToken);
            this.amqpTemplate.convertAndSend("GMALL-ORDER-EXCHANGE","order.seckill",skuLockVO);

            //倒计时，当countDownLatch为0时，用户才能访问订单
            RCountDownLatch countDownLatch = this.redissonClient.getCountDownLatch("count:down:" + orderToken);
            countDownLatch.trySetCount(1);
            countDownLatch.countDown();


            semaphore.release();
            //响应成功
            return Resp.ok("恭喜你，秒杀成功！赶紧付款吧");
        }

    }
    @GetMapping("seckill/{orderToken}")
    public Resp<Object> querySeckill(@PathVariable("orderToken")String orderToken) throws InterruptedException {
        RCountDownLatch countDownLatch = this.redissonClient.getCountDownLatch("count:down:" + orderToken);
        countDownLatch.await();

        //查询订单，并响应
        //发送feign请求 查询订单
        return null;

    }


}
