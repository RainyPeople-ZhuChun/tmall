package com.rainypeople.tmall.service.impl;

import com.rainypeople.tmall.mapper.ProductMapper;
import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.ProductExample;
import com.rainypeople.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;


    @Override
    public List<Product> list(int cid) {
        ProductExample example=new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List<Product> ps = productMapper.selectByExample(example);
        return ps;
    }

    @Override
    public void add(Product p) {
        productMapper.insert(p);
    }

    @Override
    public Product getById(int id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updata(Product p) {
        productMapper.updateByPrimaryKey(p);
    }
}
