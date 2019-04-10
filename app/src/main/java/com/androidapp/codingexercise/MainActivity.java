package com.androidapp.codingexercise;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;


import com.androidapp.codingexercise.model.Model;
import com.androidapp.codingexercise.utils.NetworkHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener {

    ReposAdapter reposAdapter;
    RecyclerView recyclerView;
    int flag = 0;
    private boolean networkOk;
    private GithubViewModel githubViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getRepos("android");

        recyclerView = findViewById(R.id.main_rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        int resId = R.anim.layout_animation_slide_from_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        recyclerView.setLayoutAnimation(animation);

        githubViewModel = new GithubViewModel(
                new GithubInteractorImpl(), AndroidSchedulers.mainThread());


        networkOk = NetworkHelper.hasNetworkAccess(this);
        if (networkOk == true) {

            Toast.makeText(this, "Network Ok", Toast.LENGTH_SHORT).show();

            if(flag == 0){
                getRepos("android", flag);
            }

        } else {
          displayNoNetworkDialog();
        }


    }


    private void getRepos(String userInput, int flag) {

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
                        if(flag == 1){
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                        recyclerView.scheduleLayoutAnimation();
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

    private void runLayoutAnimation(RecyclerView recyclerView) {

            final Context context = recyclerView.getContext();
            final LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_from_bottom);

            recyclerView.setLayoutAnimation(controller);
        }



    private void displayNoNetworkDialog() {
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();

            alertDialog.setTitle("Info");
            alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            alertDialog.show();
        } catch (Exception e) {
            Log.d("MainActivity", "Show Dialog: " + e.getMessage());
        }
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
        flag = 1;
        runLayoutAnimation(recyclerView);
        getRepos(userInput,flag);
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
