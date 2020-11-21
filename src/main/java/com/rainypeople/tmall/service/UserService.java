package com.rainypeople.tmall.service;

import com.rainypeople.tmall.pojo.User;

import java.util.List;

public interface UserService {
    List<User> list();

    void add(User user);

    boolean isExist(String name);

    User get(String name, String password);
}
