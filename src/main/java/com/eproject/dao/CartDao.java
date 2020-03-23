package com.eproject.dao;

import com.eproject.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateQuantity(Cart cart);//

    int updateDeleteStatus(Integer userId,Integer productId,@Param("ids") Integer[] ids);

    int clearCart(Integer userId);

    Cart selectProductExist(Integer userId,Integer productId);

    int updateByPrimaryKey(Cart record);

    List<Cart> selectCartInfo(Integer userId);
}