package com.example.smartnotesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

        @SuppressLint("SimpleDateFormat") final SimpleDateFormat inputFormat = new SimpleDateFormat();
        Date date = null;
        try {
            date = inputFormat.parse(post.get(position).getPost_time());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String ago = (String) DateUtils.getRelativeTimeSpanString(Objects.requireNonNull(date).getTime() , Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS);
        holder.post_time.setText(ago);

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

