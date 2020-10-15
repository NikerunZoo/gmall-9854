package com.atguigu.gmall.cart.api;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.cart.pojo.Cart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author NikerunZoo
 * @date 2020/10/14 0014 12:04
 */
public interface GmallCartApi {

    @GetMapping("cart/{userId}")
    public Resp<List<Cart>> queryCheckCartsByUserId(@PathVariable("userId")Long userId);
}