package com.eproject.dao;

import com.eproject.domain.OrderDetail;
import com.eproject.entity.Order;
import com.eproject.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PortalOrderDao {

    /**
     * 获取订单及下单商品详情
     */
    OrderDetail getDetail(@Param("orderId") Integer orderId);

    /**
     * 修改 sku_stock表的锁定库存及真实库存
     */
    int updateSkuStock(@Param("itemList") List<OrderItem> orderItemList);

    /**
     * 解除取消订单的库存锁定
     */
    int releaseSkuStockLock(@Param("itemList") List<OrderItem> orderItemList);
}
