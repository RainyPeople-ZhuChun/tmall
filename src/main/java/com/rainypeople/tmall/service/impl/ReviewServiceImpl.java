package com.rainypeople.tmall.service.impl;

import com.rainypeople.tmall.mapper.ReviewMapper;
import com.rainypeople.tmall.mapper.UserMapper;
import com.rainypeople.tmall.pojo.Review;
import com.rainypeople.tmall.pojo.ReviewExample;
import com.rainypeople.tmall.pojo.User;
import com.rainypeople.tmall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Review> list(int pid) {
        ReviewExample example=new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");
        List<Review> reviews = reviewMapper.selectByExample(example);
        setUser(reviews);
        return reviews;
    }

    @Override
    public int getReviewCount(int pid) {
        ReviewExample example=new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        List<Review> reviews = reviewMapper.selectByExample(example);

        int reviewCount=reviews.size();
        return reviewCount;
    }

    private void setUser(List<Review> reviews) {
        for (Review review:reviews){
            setUser(review);
        }
    }

    private void setUser(Review review) {
        Integer uid = review.getUid();
        User user = userMapper.selectByPrimaryKey(uid);
        review.setUser(user);
    }
}
