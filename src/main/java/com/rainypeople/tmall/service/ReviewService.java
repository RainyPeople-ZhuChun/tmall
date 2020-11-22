package com.rainypeople.tmall.service;

import com.rainypeople.tmall.pojo.Review;

import java.util.List;

public interface ReviewService {
    List<Review> list(int pid);

    int getReviewCount(int pid);
}
