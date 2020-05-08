package com.eproject.service.serverIm;

import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.dao.OrderDao;
import com.eproject.dao.OrderItemDao;
import com.eproject.domain.OrderDeliveryParam;
import com.eproject.domain.ReceiverInfoParam;
import com.eproject.entity.Order;
import com.eproject.entity.OrderItem;
import com.eproject.service.AdminOrderService;
import com.eproject.util.NumberUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class AdminOrderServiceIm implements AdminOrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderItemDao orderItemDao;

    @Override
    public PageResult orderItmList(PageQuery pageQuery){
        List<Order> orderList = orderDao.selectList(pageQuery);
        int total = orderDao.getOrderPage(pageQuery);
        PageResult pageResult = new PageResult(orderList, total, pageQuery.getLimit(), pageQuery.getPage());
        return pageResult;
    }

    @Override
    public Order getOrderById(Integer id){
        Order temp = orderDao.selectByPrimaryKey(id);
        if (temp == null){
            return null;
        }
        return temp;
    }

    @Override
    public int delivery(List<OrderDeliveryParam> deliveryParamList){
        //批量发货
        int count = orderDao.delivery(deliveryParamList);
        orderItemDao.delivery(deliveryParamList);
        return count;
    }

    @Override
    public int checkDone(String[] ids){
        //批量发货
        int count = orderDao.checkDone(Arrays.asList(ids));
        orderItemDao.checkDone(Arrays.asList(ids));
        return count;
    }

    @Override
    public int closeOrder(Integer[] id){
        Order order = new Order();
        //商家关闭
        order.setOrderStatus(-3);
        int count = orderDao.updateByOrderStatus(id);
        orderItemDao.updateByOrderStatus(id);
        return count;
    }

    @Override
    public int delete(Integer[] ids){
        Order order = new Order();
        //删除订单
        order.setDaleteStatus(1);
        int count = orderDao.updateByDeleteStatus(ids);
        //orderItemDao.updateByOrderStatus(ids);
        return count;
    }

    @Override
    public PageResult selectItemInfo(PageQuery pageQuery){
        List<OrderItem> itemList = orderItemDao.selectItemList(pageQuery);
        int total =itemList.size();
        PageResult result = new PageResult(itemList, total, pageQuery.getLimit(), pageQuery.getPage());
        return result;
    }

    @Override
    public int updateReceiverDetailAddress(Order order){
        //后端修改
        order.setUpdataTime(new Date());
        int num = orderDao.updateOrderInfo(order);
        return num;
    }
}
