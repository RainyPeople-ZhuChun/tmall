package com.rainypeople.tmall.service.impl;

import com.rainypeople.tmall.mapper.ProductMapper;
import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.ProductExample;
import com.rainypeople.tmall.pojo.ProductImage;
import com.rainypeople.tmall.service.CategoryService;
import com.rainypeople.tmall.service.ProductImageService;
import com.rainypeople.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;


    @Override
    public List<Product> list(int cid) {
        ProductExample example=new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List<Product> ps = productMapper.selectByExample(example);
        setFirstProductImage(ps);
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

    @Override
    public void setFirstProductImage(Product p) {
        List<ProductImage> pis = productImageService.list(p.getId(), ProductImageService.type_single);
        if (!pis.isEmpty()) {
            ProductImage pi = pis.get(0);
            p.setFirstProductImage(pi);
        }
    }

    public void setFirstProductImage(List<Product> ps) {
        for (Product p : ps) {
            setFirstProductImage(p);
        }
    }
}
