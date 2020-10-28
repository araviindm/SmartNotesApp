package com.example.smartnotesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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


import java.util.ArrayList;
import java.util.List;

import static com.example.smartnotesapp.FollowArray.followArrayList;


public class SearchActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private RecyclerView postList;
    PostAdapter adapter;
    EditText searchQuery;
    ImageButton searchButton;
    LinearLayout followLinearLayout;
    Button followButtonInSearch;
    FirebaseUser firebaseUser;
    TextView tagView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        postList = findViewById(R.id.all_users_post_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        postList.setLayoutManager(linearLayoutManager);
        postList.setAdapter(null);

        searchQuery = findViewById(R.id.search_query);
        searchButton = findViewById(R.id.search_image_button);
        followLinearLayout = findViewById(R.id.follow_LinearLayout);
        followButtonInSearch = findViewById(R.id.follow_button_in_search);
        tagView = findViewById(R.id.tag_view);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpRecyclerView();
                followLinearLayout.setVisibility(LinearLayout.VISIBLE);
                adapter.startListening();

            }
        });
        searchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    setUpRecyclerView();
                    followLinearLayout.setVisibility(LinearLayout.VISIBLE);
                    adapter.startListening();
                }
                return false;
            }
        });

        followButtonInSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String searchText = searchQuery.getText().toString();
                    searchText = searchText.toLowerCase();
                    if(!followButtonInSearch.getText().equals("following")) {
                        if (!followArrayList.contains(searchText)) {
                            followArrayList.add(searchText);
                            databaseReference.child("following").child(firebaseUser.getUid()).push().setValue(searchText);
                        }
                    }
            }
        });
    }
    public void setUpRecyclerView() {
        searchQuery.clearFocus();
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchQuery.getWindowToken(), 0);
        String searchText = searchQuery.getText().toString();
        searchText = searchText.toLowerCase();
        tagView.setText(searchText);
        Query query = databaseReference.child("Post").orderByChild("tag").equalTo(searchText);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String searchText = searchQuery.getText().toString();
                searchText = searchText.toLowerCase();
                for(DataSnapshot snap : dataSnapshot.child("following").child(firebaseUser.getUid()).getChildren()){

                    if( searchText.equals(snap.getValue().toString()) ){
                        followButtonInSearch.setBackgroundColor(Color.WHITE);
                        followButtonInSearch.setText(R.string.following);
                        followButtonInSearch.setTextColor(Color.BLACK);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        FirebaseRecyclerOptions<Posts> options = new FirebaseRecyclerOptions.Builder<Posts>()
                .setQuery(query, Posts.class)
                .build();

        adapter = new PostAdapter(options);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        postList.setLayoutManager(linearLayoutManager);
        postList.setAdapter(adapter);
    }
    public void onBackPressed(){
        Intent backIntent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(backIntent);
    }

   
}
