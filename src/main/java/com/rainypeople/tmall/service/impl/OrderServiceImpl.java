package com.rainypeople.tmall.service.impl;

import com.rainypeople.tmall.mapper.OrderMapper;
import com.rainypeople.tmall.mapper.UserMapper;
import com.rainypeople.tmall.pojo.Order;
import com.rainypeople.tmall.pojo.OrderExample;
import com.rainypeople.tmall.pojo.OrderItem;
import com.rainypeople.tmall.pojo.User;
import com.rainypeople.tmall.service.OrderItemService;
import com.rainypeople.tmall.service.OrderService;
import com.rainypeople.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    OrderItemService orderItemService;

    @Override
    public List<Order> list() {
        OrderExample example=new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> os = orderMapper.selectByExample(example);
        setUser(os);
        return os;
    }

    @Override
    public void updata(Order o) {
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.waitConfirm);
        orderMapper.updateByPrimaryKeySelective(o);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public float add(List<OrderItem> ois, Order order) {
        float total=0;
        add(order);
        if (false){
            throw new RuntimeException();
        }
        for (OrderItem oi:ois){
            oi.setOid(order.getId());
            orderItemService.updata(oi);
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
        }
        return total;
    }

    @Override
    public Order get(int oid) {
        Order o = orderMapper.selectByPrimaryKey(oid);
        return o;
    }

    @Override
    public void edit(Order o) {
        orderMapper.updateByPrimaryKeySelective(o);
    }

    public void add(Order order) {
        orderMapper.insert(order);
    }

    public void setUser(List<Order> os){
        for (Order o:os){
            setUser(o);
        }
    }

    public void setUser(Order o){
        User user = userMapper.selectByPrimaryKey(o.getUid());
        o.setUser(user);
    }
}
