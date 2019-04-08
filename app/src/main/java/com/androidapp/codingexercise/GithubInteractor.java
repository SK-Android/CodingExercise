package com.androidapp.codingexercise;

import com.androidapp.codingexercise.model.Model;

import io.reactivex.Observable;

public interface GithubInteractor {

    Observable<Model> search(String search);
}
