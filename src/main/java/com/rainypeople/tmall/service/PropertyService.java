package com.rainypeople.tmall.service;

import com.rainypeople.tmall.pojo.Property;

import java.util.List;

public interface PropertyService {
    List<Property> list(int cid);

    void add(Property p);

    void delete(int id);

    Property getById(int id);

    void updata(Property p);
}
