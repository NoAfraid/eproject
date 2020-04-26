package com.eproject.service;

import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.domain.OrderDeliveryParam;
import com.eproject.domain.ReceiverInfoParam;
import com.eproject.entity.Order;
import com.eproject.entity.OrderItem;


import java.util.List;

// 后台订单处理服务
public interface AdminOrderService {

    /**
     * 获取订单详情 （后台）
     *
     * */
    PageResult orderItmList(PageQuery pageQuery);
//    List<OrderItem> orderItmList(PageQuery pageQuery);

    /**
     * 根据id获取信息
     */
    Order getOrderById(Integer id);

    /**
     * 批量发货（后台）
     */
    int checkDone(String[] ids);

    /**
     * 批量发货（后台）
     */
    int delivery(List<OrderDeliveryParam> deliveryParamList);

    /**
     * 批量关闭订单
     */
    int closeOrder(Integer[] id);
    /**
     * 批量删除订单
     */
    int delete(Integer[] ids);
    /**
     * 条件模糊查询订单，包括分页
     */
    PageResult selectItemInfo(PageQuery pageQuery);
    /**
     * 修改订单收货人信息
     */
    int updateReceiverDetailAddress(Order order);
    /**
     * 修改订单费用信息
     */


}
