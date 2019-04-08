package com.androidapp.codingexercise;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.androidapp.codingexercise.model.Model;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class GithubViewModel {
    private GithubInteractor interactor;
    private Scheduler scheduler;

    public GithubViewModel(GithubInteractor interactor, Scheduler scheduler) {
        this.interactor = interactor;
        this.scheduler = scheduler;
    }
    public Observable<Model> search(String search){
        return interactor.search(search).observeOn(scheduler);
    }
}
