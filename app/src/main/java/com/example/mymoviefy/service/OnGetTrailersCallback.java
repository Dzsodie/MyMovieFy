package com.example.mymoviefy.service;

import com.example.mymoviefy.model.Trailer;

import java.util.List;

public interface OnGetTrailersCallback {

    void onSuccess(List<Trailer> trailers);
    void onError();

}
