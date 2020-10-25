package com.example.smartnotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private List tags = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        Objects.requireNonNull(getSupportActionBar()).hide();

        tagList = findViewById(R.id.all_users_post_list);
        nextButton = findViewById(R.id.next_button);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        tagList = findViewById(R.id.all_users_post_list);
        setUpRecyclerView();
        adapter = new TagAdapter(tags,getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        tagList.setLayoutManager(manager);
        tagList.setAdapter(adapter);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followArrayList.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please click one", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
            }
        });
    }
    public void setUpRecyclerView() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // List<String> following = new ArrayList<>();
                for(DataSnapshot snap : dataSnapshot.child("Post").getChildren()){
                    Posts tag = snap.getValue(Posts.class);
                       // Tags tag = (Tags) snap.child("tag").getValue();
                        tags.add(tag);
                        adapter.notifyDataSetChanged();


                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
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
