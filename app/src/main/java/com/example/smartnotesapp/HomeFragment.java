package com.example.smartnotesapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment {


    EditText editTextTitle,editTextTag,editTextPost;
    Button postButton;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private RecyclerView postList;
    PostAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container,    false);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextTitle = (EditText) rootView.findViewById(R.id.edit_text_title);
        editTextTag = (EditText) rootView.findViewById(R.id.edit_text_tag);
        editTextPost = (EditText) rootView.findViewById(R.id.edit_text_post);
        postButton = (Button) rootView.findViewById(R.id.edit_text_post_button);

        postList = (RecyclerView) rootView.findViewById(R.id.all_users_post_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        postList.setLayoutManager(linearLayoutManager);
        postList.setAdapter(null);

        firebaseAuth= FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            getActivity().finish();
            startActivity(new Intent(getActivity().getApplicationContext(),LoginActivity.class));
        }
        final DateFormat dateFormat = new SimpleDateFormat();
        
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = User.name;
                String title = editTextTitle.getText().toString();
                String tag = editTextTag.getText().toString();
                String post = editTextPost.getText().toString();
                Date time = Calendar.getInstance().getTime();
                String post_time = dateFormat.format(time);

                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = user.getUid();

                Posts newpost = new Posts(uid,name,title,tag,post,post_time);
                databaseReference.child("Post").push().setValue(newpost);
                Toast.makeText(getActivity().getApplicationContext(),"posting",Toast.LENGTH_LONG).show();
            }
        });
        setUpRecyclerView();
        return rootView;
        
    }
    public void setUpRecyclerView(){
        Query query = FirebaseDatabase.getInstance().getReference().child("Post");


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
