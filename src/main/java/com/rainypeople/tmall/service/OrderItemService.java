package com.rainypeople.tmall.service;

import com.rainypeople.tmall.pojo.Order;
import com.rainypeople.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {
    void fill(List<Order> os);

    int getSaleCount(Integer id);

    void add(OrderItem oi);

    OrderItem getById(Integer id);

    List<OrderItem> listByUser(Integer id);

    void changeOrderItem(OrderItem oi);

    void deleteOrderItem(int oiid);

    void updata(OrderItem oi);

    void fill(Order o);
}
