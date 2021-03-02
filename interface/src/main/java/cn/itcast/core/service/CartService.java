package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> addItemToCartList(List<Cart> cartList, Long itemId, Integer num);
    void setCartListToRedis(List<Cart> cartList,String userName);
    List<Cart> getCartListFromRedis(String userName);
    List<Cart> mergeCookieCartListToRedisCartList(List<Cart> cookieCartList,List<Cart> redisCartList);
}
