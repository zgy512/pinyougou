package cn.itcast.core;

import cn.itcast.core.pojo.entity.Cart;
import cn.itcast.core.service.CartService;

import java.util.List;

public class CartServiceImpl implements CartService {

    @Override
    public List<Cart> addItemToCartList(List<Cart> cartList, Long itemId, Integer num) {
        //1 根据sku id 查询sku
        //2 获取商家id
        //3 根据商家id 判断购物车列表是否有该商家的购物车
        //4 cart ==null
            // 创建购物车 并添加到 cartList
        //5 cart!=null
            // 购物车中存在商家id  查询购物车是否 有 该商品更改数量，金额
            //
        return null;
    }

    @Override
    public void setCartListToRedis(List<Cart> cartList, String userName) {

    }

    @Override
    public List<Cart> getCartListFromRedis(String userName) {
        return null;
    }

    @Override
    public List<Cart> mergeCookieCartListToRedisCartList(List<Cart> cookieCartList, List<Cart> redisCartList) {
        return null;
    }
}
