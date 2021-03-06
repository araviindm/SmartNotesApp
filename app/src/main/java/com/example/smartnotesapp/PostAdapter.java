package com.example.smartnotesapp;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class PostAdapter extends FirebaseRecyclerAdapter<Posts, PostAdapter.PostsViewHolder> {

    public PostAdapter(@NonNull FirebaseRecyclerOptions<Posts> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position, @NonNull Posts model) {
            holder.name.setText(model.getName());
            holder.surName.setText(model.getSurName());
            holder.title.setText(model.getTitle());
            holder.tag.setText(model.getTag());
            holder.post.setText(model.getPost());
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat inputFormat = new SimpleDateFormat();
        Date date = null;
        try {
            date = inputFormat.parse(model.getPost_time());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String ago = (String) DateUtils.getRelativeTimeSpanString(Objects.requireNonNull(date).getTime() , Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS);
        holder.post_time.setText(ago);


    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post,
                viewGroup, false);
        return new PostsViewHolder(v);
    }
    public class PostsViewHolder extends RecyclerView.ViewHolder {

        TextView name,surName,title,tag,post,post_time;
        public PostsViewHolder(@NonNull View itemView) {
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
