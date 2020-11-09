package com.example.smartnotesapp;

import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;




import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment {


    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private RecyclerView postList;
    HomePostAdapter adapter;
    private List post = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container,    false);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        if (firebaseAuth.getCurrentUser() == null){
            getActivity().finish();
            startActivity(new Intent(getActivity().getApplicationContext(),LoginActivity.class));
        }


        postList = rootView.findViewById(R.id.all_users_post_list);
        setUpRecyclerView();
        adapter = new HomePostAdapter(post,getActivity().getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        postList.setLayoutManager(manager);
        postList.setAdapter(adapter);





        return rootView;
        
    }
    public void setUpRecyclerView(){
        final FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> following = new ArrayList<>();
                for(DataSnapshot snap : dataSnapshot.child("following").child(Objects.requireNonNull(firebaseUser).getUid()).getChildren()){
                    following.add(Objects.requireNonNull(snap.getValue()).toString());
                }
               for(DataSnapshot snap : dataSnapshot.child("Post").getChildren()){
                   Posts posts = snap.getValue(Posts.class);
                   if(following.contains(Objects.requireNonNull(posts).getTag())){
                       post.add(posts);
                       adapter.notifyDataSetChanged();
                   }
               }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.notifyDataSetChanged();
    }
}
