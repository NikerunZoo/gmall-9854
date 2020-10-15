package com.atguigu.gmall.cart.contoller;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.cart.interceptors.LoginInterceptor;
import com.atguigu.gmall.cart.pojo.Cart;
import com.atguigu.gmall.cart.pojo.UserInfo;
import com.atguigu.gmall.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author NikerunZoo
 * @date 2020/10/13 0013 14:45
 */
@RestController
@RequestMapping("cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping
    public Resp<Object> addCart(@RequestBody Cart cart){
        this.cartService.addCart(cart);
        return Resp.ok(null);
    }

    @GetMapping
    public Resp<List<Cart>> queryCarts(){

        List<Cart> carts = this.cartService.queryCarts();
        return Resp.ok(carts);

    }

    @GetMapping("{userId}")
    public Resp<List<Cart>> queryCheckCartsByUserId(@PathVariable("userId")Long userId){
        List<Cart>  carts = this.cartService.queryCheckCartsByUserId(userId);
        return Resp.ok(carts);
    }

    @GetMapping("update")
    public Resp<Object> updateCart(@RequestBody Cart cart){
        this.cartService.updateCart(cart);
        return Resp.ok(null);

    }

    @PostMapping("delete/{skuId}")
    public Resp<Object> deleteCart(@PathVariable("skuId")Long skuId){

        this.cartService.deletCart(skuId);
        return Resp.ok(null);
    }




}
