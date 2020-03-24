package com.eproject.domain;

import com.eproject.entity.Order;
import com.eproject.entity.OrderItem;

import java.util.List;

public class OrderDetail extends Order {
    private List<OrderItem> orderItemList;

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
