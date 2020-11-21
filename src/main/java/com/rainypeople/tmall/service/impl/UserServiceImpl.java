package com.rainypeople.tmall.service.impl;

import com.rainypeople.tmall.mapper.UserMapper;
import com.rainypeople.tmall.pojo.User;
import com.rainypeople.tmall.pojo.UserExample;
import com.rainypeople.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        UserExample example=new UserExample();
        example.setOrderByClause("id desc");
        List<User> us = userMapper.selectByExample(example);
        return us;
    }
}
