package com.rainypeople.tmall.service.impl;

import com.rainypeople.tmall.mapper.ProductMapper;
import com.rainypeople.tmall.pojo.Category;
import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.ProductExample;
import com.rainypeople.tmall.pojo.ProductImage;
import com.rainypeople.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ProductService productService;


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

    @Override
    public void fill(List<Category> cs) {
        for(Category c:cs){
            fill(c);
        }
    }

    public void fill(Category c) {
        int cid=c.getId();
        ProductExample example=new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List<Product> ps = productMapper.selectByExample(example);
        setFirstProductImage(ps);
        c.setProducts(ps);
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example=new ProductExample();
        example.createCriteria().andNameLike("%"+keyword+"%");
        example.setOrderByClause("id desc");
        List<Product> ps = productMapper.selectByExample(example);
        setFirstProductImage(ps);
        setCategory(ps);
        return ps;
    }

    public void setCategory(List<Product> ps) {
        for (Product p:ps){
            setCategory(p);
        }
    }

    public void setCategory(Product p) {
        Integer cid = p.getCid();
        Category c = categoryService.getById(cid);
        p.setCategory(c);
    }

    @Override
    public void fillByRow(List<Category> cs) {
        for (Category c:cs){
            fillByRow(c);
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product p) {
        int pid=p.getId();
        int saleCount=orderItemService.getSaleCount(pid);
        p.setSaleCount(saleCount);

        int reviewCount=reviewService.getReviewCount(pid);
        p.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> ps) {
        for (Product p:ps){
            setSaleAndReviewNumber(p);
        }
    }

    private void fillByRow(Category c) {
        //productNumberEachRow是将products集合分为8个一组，方便首页分组
        int productNumberEachRow=8;
        List<Product> products =c.getProducts();
        List<List<Product>> list=new ArrayList<>();
        //将products分组，每个组8个product信息
        for(int i=0;i<products.size();i+=productNumberEachRow){
            //计数，比如0到7，8-15将products分为8个一组
            int size=i+productNumberEachRow;
            //判断最后一页的个数
            size=size>products.size()?products.size():size;
            List<Product> productsOfEachRow=products.subList(i,size);
            list.add(productsOfEachRow);
        }
        setFirstProductImage(products);
        c.setProductsByRow(list);
    }

    public void setFirstProductImage(List<Product> ps) {
        for (Product p : ps) {
            setFirstProductImage(p);
        }
    }
}
