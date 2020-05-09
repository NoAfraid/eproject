package com.eproject.dao;

import com.eproject.common.PageQuery;
import com.eproject.domain.OrderDeliveryParam;
import com.eproject.domain.OrderDetail;
import com.eproject.entity.Alipay;
import com.eproject.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectByUserId(PageQuery pageQuery);

    int getOrderPage(PageQuery pageQuery);

    int getPage(PageQuery pageQuery);

    int updateByPrimaryKeySelective(Order record);

    int updateByOrderStatus(@Param("id") Integer[] id);

    int updateByDeleteStatus(@Param("id") Integer[] id);

    int updateByPrimaryKey(Order record);

    int updateOrderInfo(Order order);

    /**
     * 获取超时订单
     * @param minute 超时时间（分）
     */
    List<OrderDetail> getTimeOutOrders(@Param("minute") Integer minute);

    /**
     * 批量修改订单状态
     */
    int updateOrderStatus(@Param("ids") List<Integer> ids,@Param("status") Integer status);

    /**
     * 手动取消订单
     */
    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    /**
     * 筛选订单信息
     * @param orderNo
     * @return
     */
    Order selectByOrderNo(String orderNo);

    List<Order> selectByOrderNoList(String orderNo);
    /**
     * 批量配货
     */
    int checkDone(@Param("list") List<String> ids);

    /**
     * 批量发货
     */
    int delivery(@Param("list") List<OrderDeliveryParam> deliveryParamList);

    /**
     * 获取订单信息
     */
    List<Order> selectList(PageQuery pageQuery);

    /**
     * 退货更新状态
     */
    void updateStatus(Alipay alipay);
}