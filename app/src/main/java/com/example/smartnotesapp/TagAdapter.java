package com.example.smartnotesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.smartnotesapp.FollowArray.followArrayList;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.MyViewHolder> {

    List<Posts> post;

    public TagAdapter(List<Posts> post, Context applicationContext) {
        this.post = post;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


      holder.tag.setText(post.get(position).getTag());
        holder.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!followArrayList.contains((String) holder.tag.getText())) {
                    followArrayList.add((String) holder.tag.getText());
                }
                databaseReference.child("following").child(firebaseUser.getUid()).setValue(followArrayList);
            }
        });

    }


    @Override
    public int getItemCount() {
        return post.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tag;
        Button followButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.u_tag);
            followButton = itemView.findViewById(R.id.follow_button);

        }
    }
}

