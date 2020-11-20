package com.rainypeople.tmall.service.impl;

import com.rainypeople.tmall.mapper.PropertyMapper;
import com.rainypeople.tmall.pojo.Property;
import com.rainypeople.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public List<Property> list() {
        return propertyMapper.list();
    }
}
