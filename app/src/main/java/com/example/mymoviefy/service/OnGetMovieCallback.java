package com.example.mymoviefy.service;

import com.example.mymoviefy.model.Movie;

public interface OnGetMovieCallback {

    void onSuccess(Movie movie);
    void onError();
}
