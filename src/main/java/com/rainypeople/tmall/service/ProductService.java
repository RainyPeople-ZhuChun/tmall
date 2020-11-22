package com.rainypeople.tmall.service;

import com.rainypeople.tmall.pojo.Category;
import com.rainypeople.tmall.pojo.Product;

import java.util.List;

public interface ProductService {
    List<Product> list(int cid);

    void add(Product p);

    Product getById(int id);

    void delete(int id);

    void updata(Product p);

    void setFirstProductImage(Product p);

    void fill(List<Category> cs);

    void fillByRow(List<Category> cs);

    void setSaleAndReviewNumber(Product p);

    void setSaleAndReviewNumber(List<Product> ps);

    void fill(Category c);

    List<Product> search(String keyword);
}
