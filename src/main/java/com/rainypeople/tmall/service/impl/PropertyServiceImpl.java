package com.rainypeople.tmall.service.impl;

import com.rainypeople.tmall.mapper.PropertyMapper;
import com.rainypeople.tmall.pojo.Property;
import com.rainypeople.tmall.pojo.PropertyExample;
import com.rainypeople.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public List<Property> list(int cid) {

        PropertyExample example =new PropertyExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        return propertyMapper.selectByExample(example);
    }

    @Override
    public void add(Property p) {
        propertyMapper.insert(p);
    }

    @Override
    public void delete(int id) {
        propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Property getById(int id) {
        return propertyMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updata(Property p) {
        propertyMapper.updateByPrimaryKeySelective(p);
    }


}
