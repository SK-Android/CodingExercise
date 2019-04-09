package com.androidapp.codingexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;


import com.androidapp.codingexercise.model.Model;
import com.androidapp.codingexercise.service.ApiInterface;
import com.github.florent37.tutoshowcase.TutoShowcase;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener {

    ReposAdapter reposAdapter;
    RecyclerView recyclerView;

    private GithubViewModel githubViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getRepos("android");

        githubViewModel = new GithubViewModel(
                new GithubInteractorImpl(), AndroidSchedulers.mainThread());


    }




    private void getRepos(String userInput) {

        githubViewModel.search(userInput)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Model>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Model model) {
                        reposAdapter = new ReposAdapter(model.getItems(),getBaseContext());
                        //Log.i("MainActivity", model.getItems().toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainACtivity",e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        recyclerView.setAdapter(reposAdapter);
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        recyclerView = findViewById(R.id.main_rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        getRepos(userInput);
        return false;
    }

    @Override
    public void onBackPressed() {
        if(recyclerView.getVisibility()== View.VISIBLE){
            recyclerView.setVisibility(View.INVISIBLE);
            return;
        }
        super.onBackPressed();
    }
}
