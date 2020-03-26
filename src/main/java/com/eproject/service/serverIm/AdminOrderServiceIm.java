package com.eproject.service.serverIm;

import com.eproject.common.PageQuery;
import com.eproject.dao.OrderDao;
import com.eproject.dao.OrderItemDao;
import com.eproject.domain.OrderDeliveryParam;
import com.eproject.entity.Order;
import com.eproject.entity.OrderItem;
import com.eproject.service.AdminOrderService;
import com.eproject.util.NumberUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminOrderServiceIm implements AdminOrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderItemDao orderItemDao;

    @Override
    public List<OrderItem> orderItmList(PageQuery pageQuery){
        return orderItemDao.selectList(pageQuery);
    }

    @Override
    public int delivery(List<OrderDeliveryParam> deliveryParamList){
        //批量发货
        int count = orderDao.delivery(deliveryParamList);
        return count;
    }

    @Override
    public int closeOrder(List<Integer> id){
        Order order = new Order();
        //商家关闭
        order.setOrderStatus(-3);
        int count = orderDao.updateByOrderStatus(id);
        return count;
    }

    @Override
    public int delete(List<Integer> id){
        Order order = new Order();
        //删除订单
        order.setDaleteStatus(1);
        int count = orderDao.updateByDeleteStatus(id);
        return count;
    }

    @Override
    public List<OrderItem> selectItemInfo(PageQuery pageQuery){
        List<OrderItem> itemList = orderItemDao.selectItemList(pageQuery);
        return itemList;
    }
}
