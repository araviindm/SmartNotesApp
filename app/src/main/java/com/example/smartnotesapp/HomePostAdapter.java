package com.example.smartnotesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.MyViewHolder> {

    List<Posts> post;

    public HomePostAdapter(List<Posts> post, Context applicationContext) {
        this.post = post;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(post.get(position).getName());
        holder.surName.setText(post.get(position).getSurName());
        holder.title.setText(post.get(position).getTitle());
        holder.tag.setText(post.get(position).getTag());
        holder.post.setText(post.get(position).getPost());
        holder.post_time.setText(post.get(position).getPost_time());

    }


    @Override
    public int getItemCount() {
        return post.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,surName, title, tag, post, post_time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.u_name);
            surName = itemView.findViewById(R.id.u_last_name);
            title = itemView.findViewById(R.id.u_title);
            tag = itemView.findViewById(R.id.u_tag);
            post = itemView.findViewById(R.id.u_post);
            post_time = itemView.findViewById(R.id.u_post_time);
        }
    }
}

