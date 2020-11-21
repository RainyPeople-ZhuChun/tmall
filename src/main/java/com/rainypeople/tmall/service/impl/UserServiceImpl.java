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

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public boolean isExist(String name) {
        boolean flag=false;
        UserExample example=new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(example);
        for (User user:users){
            if (name.equals(user.getName())){
                flag=true;
            }
        }
        return flag;
    }

    @Override
    public User get(String name, String password) {
        UserExample example=new UserExample();
        example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(example);
        if (users.isEmpty()){
            return null;
        }
        return users.get(0);
    }
}
