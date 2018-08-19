package com.example.mymoviefy.service;

import com.example.mymoviefy.model.Review;

import java.util.List;

public interface OnGetReviewsCallback {

    void onSuccess(List<Review> reviews);
    void onError();
}
