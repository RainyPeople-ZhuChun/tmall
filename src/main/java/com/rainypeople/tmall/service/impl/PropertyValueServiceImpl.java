package com.rainypeople.tmall.service.impl;

import com.rainypeople.tmall.mapper.PropertyMapper;
import com.rainypeople.tmall.mapper.PropertyValueMapper;
import com.rainypeople.tmall.pojo.Product;
import com.rainypeople.tmall.pojo.Property;
import com.rainypeople.tmall.pojo.PropertyValue;
import com.rainypeople.tmall.pojo.PropertyValueExample;
import com.rainypeople.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    private PropertyMapper propertyMapper;
    @Autowired
    private PropertyValueMapper propertyValueMapper;

    @Override
    public void init(Product p) {
        List<Property> pts=propertyMapper.list(p.getCid());
        for (Property pt:pts){
            PropertyValue pv = get(pt.getId(), p.getId());
            if (pv==null){
                pv=new PropertyValue();
                pv.setPid(p.getId());
                pv.setPtid(pt.getId());
                propertyValueMapper.insert(pv);
            }
        }
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example=new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> pvs = propertyValueMapper.selectByExample(example);
        for (PropertyValue pv:pvs){
            Property property = propertyMapper.selectByPrimaryKey(pv.getPtid());
            pv.setProperty(property);
        }
        return pvs;
    }

    @Override
    public String updata(PropertyValue pv) {
        String result="";
        int count = propertyValueMapper.updateByPrimaryKeySelective(pv);
        if (count==1){
            result="success";
        }
        return result;
    }

    public PropertyValue get(int ptid,int pid){
        PropertyValueExample example=new PropertyValueExample();
        example.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> pvs = propertyValueMapper.selectByExample(example);
        if (pvs.isEmpty()){
            return null;
        }
        return pvs.get(0);
    }
}
