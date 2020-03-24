package com.eproject.service;

import com.eproject.common.PageQuery;
import com.eproject.domain.ConfirmOrderResult;
import com.eproject.entity.Order;

import java.util.List;
import java.util.Map;

/**
 * 前台和后台的订单管理Service
 * Created by moshenjiangg 2020/3/23
 */
public interface OrderService {
    /**
     * 根据用户购物车信息生成确认单信息
     */
    ConfirmOrderResult generateConfirmOrder();

    /**
     * 根据提交信息生成订单
     */
    Map<String, Object> generateOrder(Order order);
    /**
     * 查看我的订单列表
     */
    List<Order> getMyOrders(PageQuery pageQuery);
    /**
     * 修改我的订单信息
     */

    /**
     * 支付成功后的回调
     */

    /**
     * 自动取消超时订单
     */

    /**
     * 取消单个超时订单
     */

    /**
     * 手动取消订单
     *
    */

    /**
     * 确认收货
     **/

    // 后台订单处理
    /**
     * 获取订单详情 （后台）
     *
     * */

    /**
     * 批量发货（后台）
     */

    /**
     * 条件查询订单，包括分页
     */

    /**
     * 批量关闭订单
     */

    /**
     * 批量删除订单
     */

    /**
     * 修改订单收货人信息
     */

    /**
     * 修改订单费用信息
     */

    /**
     * 发送延迟消息取消订单
     */
    int sendDelayMessageCancelOrder(Integer orderId);
}
