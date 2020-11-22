package com.rainypeople.tmall.service.impl;

import com.rainypeople.tmall.mapper.OrderItemMapper;
import com.rainypeople.tmall.mapper.ProductMapper;
import com.rainypeople.tmall.pojo.Order;
import com.rainypeople.tmall.pojo.OrderItem;
import com.rainypeople.tmall.pojo.OrderItemExample;
import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void fill(List<Order> os) {
        for (Order o:os){
            fill(o);
        }
    }

    @Override
    public int getSaleCount(Integer pid) {
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");
        List<OrderItem> ois = orderItemMapper.selectByExample(example);

        int saleCount=0;
        for (OrderItem oi:ois){
            saleCount+=oi.getNumber();
        }
        return saleCount;
    }


    private void fill(Order o) {
        int oid=o.getId();
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andOidEqualTo(oid);
        example.setOrderByClause("id desc");
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        setProduct(ois);

        float total=0;
        int totalNumber=0;
        for(OrderItem oi:ois){
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
            totalNumber+=oi.getNumber();
            o.setTotal(total);
            o.setTotalNumber(totalNumber);
        }

        o.setOrderItems(ois);
    }

    private void setProduct(List<OrderItem> ois) {
        for (OrderItem oi:ois){
            setProduct(oi);
        }
    }

    private void setProduct(OrderItem oi) {
        int pid=oi.getPid();
        Product product = productMapper.selectByPrimaryKey(pid);
        oi.setProduct(product);
    }
}
