package com.example.mymoviefy.service;

import com.example.mymoviefy.model.Movie;

import java.util.List;

public interface OnGetMoviesCallback {

    void onSuccess(int page, List<Movie> movies);
    void onError();
}
