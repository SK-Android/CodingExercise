package com.androidapp.codingexercise;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.codingexercise.model.Model;
import com.androidapp.codingexercise.model.Owner;
import com.squareup.picasso.Picasso;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.androidapp.codingexercise.ReposAdapter.REPO_DESCRIPTION;
import static com.androidapp.codingexercise.ReposAdapter.REPO_NAME;
import static com.androidapp.codingexercise.ReposAdapter.REPO_OWNER;

public class DetailFragment extends Fragment {

    GithubViewModel githubViewModel;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ImageView repoImage = view.findViewById(R.id.repo_image);
        TextView repoName = view.findViewById(R.id.repo_name_detail);
        TextView description = view.findViewById(R.id.description_detail);
        TextView url = view.findViewById(R.id.url_detail);
        githubViewModel = new GithubViewModel(
                new GithubInteractorImpl(), AndroidSchedulers.mainThread());

        Bundle arguments = getArguments();
        if(arguments != null){
           Owner repo_owner = arguments.getParcelable(REPO_OWNER);
           String repo_name = arguments.getString(REPO_NAME);
           String repo_description = arguments.getString(REPO_DESCRIPTION);

           repoName.setText(repo_name);
           Picasso.get().load(repo_owner.getAvatarUrl()).into(repoImage);
           description.setText(repo_description);
           url.setText(repo_owner.getUrl());
        }

        return view;
    }



}
