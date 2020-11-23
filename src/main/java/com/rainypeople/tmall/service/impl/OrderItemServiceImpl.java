package com.rainypeople.tmall.service.impl;

import com.rainypeople.tmall.mapper.OrderItemMapper;
import com.rainypeople.tmall.mapper.ProductMapper;
import com.rainypeople.tmall.pojo.*;
import com.rainypeople.tmall.service.OrderItemService;
import com.rainypeople.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    ProductService productService;

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

    @Override
    public void add(OrderItem oi) {
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andPidEqualTo(oi.getPid()).andUidEqualTo(oi.getUid()).andOidIsNull();
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        if (!ois.isEmpty()){
            OrderItem orderItem = ois.get(0);
            int number=orderItem.getNumber()+oi.getNumber();
            orderItem.setNumber(number);
            orderItemMapper.updateByPrimaryKey(orderItem);
            oi.setId(orderItem.getId());
        }else {
            orderItemMapper.insert(oi);
        }

    }

    @Override
    public OrderItem getById(Integer id) {
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andIdEqualTo(id);
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        setProduct(ois);
        for (OrderItem oi:ois){
            Product p = oi.getProduct();
            productService.setFirstProductImage(p);
        }
        return ois.get(0);
    }

    @Override
    public List<OrderItem> listByUser(Integer id) {
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andUidEqualTo(id).andOidIsNull();
        example.setOrderByClause("id desc");
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        setProduct(ois);
        for (OrderItem oi:ois){
            Product p = oi.getProduct();
            productService.setFirstProductImage(p);
        }
        return ois;
    }

    @Override
    public void changeOrderItem(OrderItem oi) {
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andPidEqualTo(oi.getPid()).andUidEqualTo(oi.getUid()).andOidIsNull();
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        OrderItem orderItem = ois.get(0);
        oi.setId(orderItem.getId());
        orderItemMapper.updateByPrimaryKey(oi);
    }

    @Override
    public void deleteOrderItem(int oiid) {
        orderItemMapper.deleteByPrimaryKey(oiid);
    }

    @Override
    public void updata(OrderItem oi) {
        orderItemMapper.updateByPrimaryKey(oi);
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
