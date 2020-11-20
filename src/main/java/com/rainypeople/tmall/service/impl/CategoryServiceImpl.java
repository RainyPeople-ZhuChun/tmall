package com.rainypeople.tmall.service.impl;

import com.rainypeople.tmall.mapper.CategoryMapper;
import com.rainypeople.tmall.pojo.Category;
import com.rainypeople.tmall.service.CategoryService;
import com.rainypeople.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category c) {
        categoryMapper.insert(c);
    }

    @Override
    public void delete(int id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Category getById(int id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updata(Category c) {
        categoryMapper.updateByPrimaryKey(c);
    }

    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }


}
