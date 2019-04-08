package com.androidapp.codingexercise.service;

import com.androidapp.codingexercise.model.Model;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    //?q=android
    @GET("search/repositories")
    Observable<Model> getRepos(@Query("q") String searchString);
}
