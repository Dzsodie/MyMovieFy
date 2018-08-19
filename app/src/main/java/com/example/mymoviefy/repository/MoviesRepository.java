package com.example.mymoviefy.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mymoviefy.BuildConfig;
import com.example.mymoviefy.model.GenresResponse;
import com.example.mymoviefy.model.Movie;
import com.example.mymoviefy.model.MoviesResponse;
import com.example.mymoviefy.model.ReviewResponse;
import com.example.mymoviefy.model.TrailerResponse;
import com.example.mymoviefy.service.OnGetGenresCallback;
import com.example.mymoviefy.service.OnGetMovieCallback;
import com.example.mymoviefy.service.OnGetMoviesCallback;
import com.example.mymoviefy.service.OnGetReviewsCallback;
import com.example.mymoviefy.service.OnGetTrailersCallback;
import com.example.mymoviefy.service.TMDbApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepository {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";
    public static final String UPCOMING = "upcoming";


    private static MoviesRepository repository;

    private TMDbApi api;

    private MoviesRepository(TMDbApi api) {
        this.api = api;
    }

    public static MoviesRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MoviesRepository(retrofit.create(TMDbApi.class));
        }

        return repository;
    }

    public void getMovies(int page, String sortBy, final OnGetMoviesCallback callback) {
        Callback<MoviesResponse> call = new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    MoviesResponse moviesResponse = response.body();
                    if (moviesResponse != null && moviesResponse.getMovies() != null) {
                        callback.onSuccess(moviesResponse.getPage(), moviesResponse.getMovies());
                    } else {
                        callback.onError();
                    }
                } else {
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                callback.onError();
            }
        };

        switch (sortBy) {
            case TOP_RATED:
                api.getTopRatedMovies(BuildConfig.TMDB_API_KEY, LANGUAGE, page)
                        .enqueue(call);
                break;
            case UPCOMING:
                api.getUpcomingMovies(BuildConfig.TMDB_API_KEY, LANGUAGE, page)
                        .enqueue(call);
                break;
            case POPULAR:
            default:
                api.getPopularMovies(BuildConfig.TMDB_API_KEY, LANGUAGE, page)
                        .enqueue(call);
                break;
        }
    }


    public void getMovie(int movieId, final OnGetMovieCallback callback) {
        api.getMovie(movieId, BuildConfig.TMDB_API_KEY, LANGUAGE)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.isSuccessful()) {
                            Movie movie = response.body();
                            if (movie != null) {
                                callback.onSuccess(movie);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getTrailers(int movieId, final OnGetTrailersCallback callback) {
        api.getTrailers(movieId, BuildConfig.TMDB_API_KEY, LANGUAGE)
                .enqueue(new Callback<TrailerResponse>() {
                    @Override
                    public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                        if (response.isSuccessful()) {
                            TrailerResponse trailerResponse = response.body();
                            if (trailerResponse != null && trailerResponse.getTrailers() != null) {
                                callback.onSuccess(trailerResponse.getTrailers());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<TrailerResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getReviews(int movieId, final OnGetReviewsCallback callback) {
        api.getReviews(movieId, BuildConfig.TMDB_API_KEY, LANGUAGE)
                .enqueue(
                        new Callback<ReviewResponse>() {
                            @Override
                            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                                if (response.isSuccessful()) {
                                    ReviewResponse reviewResponse = response.body();
                                    if (reviewResponse != null && reviewResponse.getReviews() != null) {
                                        callback.onSuccess(reviewResponse.getReviews());
                                    } else {
                                        callback.onError();
                                    }
                                } else {
                                    callback.onError();
                                }
                            }

                            @Override
                            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                                callback.onError();
                            }
                        });
    }

    public void getGenres(final OnGetGenresCallback callback) {
        api.getGenres(BuildConfig.TMDB_API_KEY, LANGUAGE)
                .enqueue(
                        new Callback<GenresResponse>() {
                            @Override
                            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                                if (response.isSuccessful()) {
                                    GenresResponse genresResponse = response.body();
                                    if (genresResponse != null && genresResponse.getGenres() != null) {
                                        callback.onSuccess(genresResponse.getGenres());
                                    } else {
                                        callback.onError();
                                    }
                                } else {
                                    callback.onError();
                                }
                            }

                            @Override
                            public void onFailure(Call<GenresResponse> call, Throwable t) {
                                callback.onError();
                            }
                        });
    }
}