package com.rainypeople.tmall.service;

import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {

    void init(Product p);

    List<PropertyValue> list(int pid);

    String updata(PropertyValue pv);
}
