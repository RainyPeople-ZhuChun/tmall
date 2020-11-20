package com.rainypeople.tmall.service.impl;

import com.rainypeople.tmall.mapper.ProductImageMapper;
import com.rainypeople.tmall.pojo.ProductImage;
import com.rainypeople.tmall.pojo.ProductImageExample;
import com.rainypeople.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageMapper productImageMapper;

    @Override
    public List<ProductImage> list(int pid, String type) {
        ProductImageExample example=new ProductImageExample();
        example.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
        example.setOrderByClause("id desc");
        List<ProductImage> p = productImageMapper.selectByExample(example);
        return p;
    }

    @Override
    public void add(ProductImage pi) {
        productImageMapper.insert(pi);
    }

    @Override
    public ProductImage get(int id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(int id) {
        productImageMapper.deleteByPrimaryKey(id);
    }
}
