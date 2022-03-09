package com.example.newsapp.Api;
import com.example.newsapp.Model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    public Call<News> getData(
            @Query("country") String country ,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    public Call<News> getSearch(
            @Query("q") String keyword,
            @Query("from") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );

}
