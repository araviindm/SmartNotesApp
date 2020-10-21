package com.example.smartnotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewPost extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    EditText editTextTitle,editTextTag,editTextPost;
    Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextTag = findViewById(R.id.edit_text_tag);
        editTextPost = findViewById(R.id.edit_text_post);
        postButton = findViewById(R.id.edit_text_post_button);
        firebaseAuth = FirebaseAuth.getInstance();

        final DateFormat dateFormat = new SimpleDateFormat();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

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
                    Toast.makeText(getApplicationContext(),"posting",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));

                }
            });
        }
    public void onBackPressed(){
        Intent backIntent = new Intent(this,HomeActivity.class);
        startActivity(backIntent);
    }
    }
