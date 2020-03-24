package com.eproject.service;

import com.eproject.entity.Cart;

import java.util.List;

public interface CartService {
    /**
     * 查询购物车中是否包含该商品，有增加数量，无添加到购物车
     */
    int add(Cart cart);
    /**
     * 修改某个购物车商品的数量
     */
    int updateQuantity(Cart cart);//Integer id, Integer userId, Integer productCount

    /**
     * 获取我的购物车中的列表数据
     */
    List<Cart> selectCartInfo(Integer  userId);
    /**
     * 批量删除购物车中的商品
     */
    int deleteCart(Cart cart, List<Integer> ids);//Integer[] ids
    /**
     * 清空购物车
     */
    int clear(Integer userId);
    /**
     * 获取购物车中用于选择商品规格的商品信息
     */
}
