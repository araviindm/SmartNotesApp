package com.example.smartnotesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PostAdapter extends FirebaseRecyclerAdapter<Posts, PostAdapter.PostsViewHolder> {

    public PostAdapter(@NonNull FirebaseRecyclerOptions<Posts> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position, @NonNull Posts model) {
        holder.uid.setText(model.getName());
        holder.post.setText(model.getPost());
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_post,
                viewGroup, false);
        return new PostsViewHolder(v);
    }
    public class PostsViewHolder extends RecyclerView.ViewHolder {

        TextView uid,post;
        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);

            uid = itemView.findViewById(R.id.u_name);
            post = itemView.findViewById(R.id.u_post);

        }
    }
}
