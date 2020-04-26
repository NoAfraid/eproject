package com.eproject.service;

import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.domain.ConfirmOrderResult;
import com.eproject.domain.ReceiverInfoParam;
import com.eproject.entity.Order;
import com.eproject.entity.OrderItem;
import com.eproject.entity.UserReceiveAddress;

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
    PageResult getMyOrders(PageQuery pageQuery);
    /**
     * 修改订单地址信息
     */
    int updateReceiverInfo(ReceiverInfoParam infoParam);

    /**
     * 自动取消超时订单
     */
    Integer cancelTimeOutOrder();

    /**
     * 获取用户订单购物信息
     */
    List<Order> getOrderByOrderNo(String orderNo);
    List<OrderItem> getByOrderNo(String orderNo);
    /**
     * 手动取消订单
     *
    */
    String cancelOrder(String orderNo, Integer userId);
    /**
     * 去支付
     */
    Order pay(String orderNo);
    /**
     * 支付成功后的回调
     */
    Integer paySuccess(String orderNo,String payType);

    /**
     * 确认收货
     **/
    String finishOrder(String orderNo, Integer userId);

    /**
     * 取消单个超时订单
     */

    /**
     * 发送延迟消息取消订单
     */
    int sendDelayMessageCancelOrder(Integer orderId);
}
