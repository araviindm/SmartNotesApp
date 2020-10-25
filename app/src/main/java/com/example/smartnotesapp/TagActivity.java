package com.example.smartnotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.smartnotesapp.FollowArray.followArrayList;

public class TagActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private RecyclerView tagList;
    TagAdapter adapter;
    Button nextButton;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        Objects.requireNonNull(getSupportActionBar()).hide();


        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        tagList = findViewById(R.id.all_users_post_list);
        nextButton = findViewById(R.id.next_button);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tagList.setLayoutManager(linearLayoutManager);
        tagList.setAdapter(null);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followArrayList.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please click one", Toast.LENGTH_SHORT).show();
                }else{
                  //  databaseReference.child("following").child(firebaseUser.getUid()).setValue(followArrayList);
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
            }
        });
        setUpRecyclerView();
    }
    public void setUpRecyclerView() {


        Query query = databaseReference.child("Post");

        FirebaseRecyclerOptions<Posts> options = new FirebaseRecyclerOptions.Builder<Posts>()
                .setQuery(query, Posts.class)
                .build();

        adapter = new TagAdapter(options);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        tagList.setLayoutManager(linearLayoutManager);
        tagList.setAdapter(adapter);
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
        adapter.stopListening();
    }
}
