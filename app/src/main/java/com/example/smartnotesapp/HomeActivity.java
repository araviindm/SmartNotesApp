package com.example.smartnotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private EditText editTextPost;
    private Button postButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextPost = (EditText) findViewById(R.id.edit_text_post);
        postButton = (Button) findViewById(R.id.edit_text_post_button);
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }


        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String post = editTextPost.getText().toString();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = user.getUid();
                NewPost newpost = new NewPost(uid,post);

                databaseReference.child("Post").push().setValue(newpost);
                Toast.makeText(getApplicationContext(),"posting",Toast.LENGTH_LONG).show();
            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_search:
                                Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_profile:
                                Intent intentProfile = new Intent(HomeActivity.this, ProfileActivity.class);
                                startActivity(intentProfile);
                                break;
                        }
                        return true;
                    }
                });
    }
}