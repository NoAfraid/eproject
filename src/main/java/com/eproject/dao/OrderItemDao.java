package com.eproject.dao;

import com.eproject.common.PageQuery;
import com.eproject.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemDao {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    List<OrderItem> selectList(PageQuery pageQuery);

    List<OrderItem> selectByUserId(PageQuery pageQuery);
    /**
     * 模糊查询订单
     * @param pageQuery
     * @return
     */
    List<OrderItem> selectItemList(PageQuery pageQuery);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    int insertList(@Param("list")List<OrderItem> list);
}