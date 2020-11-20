package com.rainypeople.tmall.service;

import com.rainypeople.tmall.pojo.Category;
import com.rainypeople.tmall.util.Page;

import java.util.List;

public interface CategoryService {

    void add(Category c);


    void delete(int id);

    Category getById(int id);

    void updata(Category c);

    List<Category> list();
}
