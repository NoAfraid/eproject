package com.eproject.service;

import com.eproject.common.PageQuery;
import com.eproject.domain.OrderDeliveryParam;
import com.eproject.entity.OrderItem;

import java.util.List;

// 后台订单处理服务
public interface AdminOrderService {

    /**
     * 获取订单详情 （后台）
     *
     * */
    List<OrderItem> orderItmList(PageQuery pageQuery);
    /**
     * 批量发货（后台）
     */
    int delivery(List<OrderDeliveryParam> deliveryParamList);

    /**
     * 批量关闭订单
     */
    int closeOrder(List<Integer> id);
    /**
     * 批量删除订单
     */
    int delete(List<Integer> ids);
    /**
     * 条件模糊查询订单，包括分页
     */
    List<OrderItem> selectItemInfo(PageQuery pageQuery);
    /**
     * 修改订单收货人信息
     */

    /**
     * 修改订单费用信息
     */


}
