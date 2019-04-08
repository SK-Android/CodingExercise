package com.androidapp.codingexercise;

import com.androidapp.codingexercise.model.Model;
import com.androidapp.codingexercise.service.ApiInterface;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubInteractorImpl implements GithubInteractor {

    private ApiInterface apiInterface;
    String URL = "https://api.github.com/";

    public GithubInteractorImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiInterface =retrofit.create(ApiInterface.class);
    }

    @Override
    public Observable<Model> search(String search) {
        return apiInterface.getRepos(search)
                .subscribeOn(Schedulers.io());
    }
}
