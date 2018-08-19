package com.example.mymoviefy.service;

import com.example.mymoviefy.model.Genre;

import java.util.List;

public interface OnGetGenresCallback {

    void onSuccess(List<Genre> genres);
    void onError();
}
