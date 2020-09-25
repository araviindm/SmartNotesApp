package com.example.smartnotesapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class ProfileFragment extends Fragment {

    ImageButton editProfileImageButton;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private RecyclerView postList;
    PostAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        postList = (RecyclerView) rootView.findViewById(R.id.all_users_post_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        postList.setLayoutManager(linearLayoutManager);
        postList.setAdapter(null);

        editProfileImageButton = rootView.findViewById(R.id.edit_Profile_button);

        editProfileImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
                getActivity().finish();
            }
        });

        firebaseAuth= FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            getActivity().finish();
            startActivity(new Intent(getActivity().getApplicationContext(),LoginActivity.class));
        }

        setUpRecyclerView();
        return rootView;
    }

    public void setUpRecyclerView() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();
        Query query = databaseReference.child("Post").orderByChild("uid").equalTo(uid);

        FirebaseRecyclerOptions<Posts> options = new FirebaseRecyclerOptions.Builder<Posts>()
                .setQuery(query, Posts.class)
                .build();

        adapter = new PostAdapter(options);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        postList.setLayoutManager(linearLayoutManager);
        postList.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }

    }

}
