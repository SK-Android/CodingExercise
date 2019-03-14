package com.androidapp.codingexercise;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.codingexercise.model.Item;
import com.androidapp.codingexercise.model.Model;
import com.androidapp.codingexercise.model.Owner;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

    List<Item> itemList;
    Context context;

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
        Picasso.get().load(owner.getAvatarUrl()).fit().placeholder(R.drawable.ic_launcher_background)
                .into((ImageView) viewHolder.avatarImage.findViewById(R.id.imageView));

        viewHolder.repoName.setText(itemList.get(i).getFullName());
        viewHolder.desciption.setText(itemList.get(i).getDescription());
        //viewHolder.stars.setText(itemList.get(i).get);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImage;
        TextView repoName;
        TextView desciption;
        TextView stars;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            avatarImage = itemView.findViewById(R.id.imageView);
            repoName = itemView.findViewById(R.id.repo_name);
            desciption = itemView.findViewById(R.id.description);
            stars = itemView.findViewById(R.id.stars);
        }
    }
}
