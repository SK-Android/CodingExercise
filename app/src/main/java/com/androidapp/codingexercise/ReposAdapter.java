package com.androidapp.codingexercise;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.codingexercise.model.Item;
import com.androidapp.codingexercise.model.Owner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

    public static final String REPO_OWNER = "REPO_OWNER";
    public static final String REPO_NAME = "REPO_NAME";
    public static final String REPO_DESCRIPTION = "REPO_DESCRIPTION";
    List<Item> itemList;
    Context context;

    int radius = 30; // corner radius, higher value = more rounded
    int margin = 10; // crop margin, set to 0 for corners with no crop

    public ReposAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View itemView = layoutInflater.inflate(R.layout.main_list_item,viewGroup,false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Owner owner = itemList.get(i).getOwner();
//        Picasso.get().load(owner.getAvatarUrl()).fit().placeholder(R.drawable.ic_launcher_background)
//                .into((ImageView) viewHolder.avatarImage.findViewById(R.id.imageView));


        Glide.with(context)
                .load(owner.getAvatarUrl())
                .apply(new RequestOptions().circleCrop())
                .override(350,300)
                .into((ImageView) viewHolder.avatarImage.findViewById(R.id.imageView));

        viewHolder.repoName.setText(itemList.get(i).getFullName());
        viewHolder.desciption.setText(itemList.get(i).getDescription());
        viewHolder.url.setText(itemList.get(i).getGitUrl());
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DetailFragment detailFragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(REPO_OWNER, owner);
                bundle.putString(REPO_NAME,itemList.get(i).getName());
                bundle.putString(REPO_DESCRIPTION,itemList.get(i).getDescription());

                detailFragment.setArguments(bundle);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();


                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                        .add(R.id.fragment_container, detailFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImage;
        TextView repoName;
        TextView desciption;
        TextView url;
        View view;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            avatarImage = itemView.findViewById(R.id.imageView);
            repoName = itemView.findViewById(R.id.repo_name);
            desciption = itemView.findViewById(R.id.description);
            url = itemView.findViewById(R.id.url);
            view = itemView;
        }
    }

}
