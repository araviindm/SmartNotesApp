package com.example.smartnotesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.smartnotesapp.FollowArray.followArrayList;


public class TagAdapter extends FirebaseRecyclerAdapter<Posts, TagAdapter.TagsViewHolder> {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    public TagAdapter(@NonNull FirebaseRecyclerOptions<Posts> options) {
        super(options);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    @Override
    protected void onBindViewHolder(@NonNull final TagsViewHolder holder, int position, @NonNull Posts model) {

        holder.tag.setText(model.getTag());
        holder.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!followArrayList.contains(holder.tag.getText())) {
                    followArrayList.add((String) holder.tag.getText());
                }
                databaseReference.child("following").child(firebaseUser.getUid()).setValue(followArrayList);
            }
        });


    }

    @NonNull
    @Override
    public TagsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tag_list,
                viewGroup, false);
        return new TagsViewHolder(v);
    }
    public class TagsViewHolder extends RecyclerView.ViewHolder {

        TextView tag;
        Button followButton;
        public TagsViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.u_tag);
            followButton = itemView.findViewById(R.id.follow_button);


        }
    }
}
