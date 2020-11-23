package com.rainypeople.tmall.service;

import com.rainypeople.tmall.pojo.Order;
import com.rainypeople.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderService {
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    List<Order> list();


    void updata(Order o);

    float add(List<OrderItem> ois, Order order);

    Order get(int oid);

    void edit(Order o);

    List<Order> list(Integer uid, String delete);
}
