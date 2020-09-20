package com.example.smartnotesapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomeFragment extends Fragment {

    DatabaseReference databaseReference;
    EditText editTextPost;
    Button postButton;
    FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container,    false);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextPost = (EditText) rootView.findViewById(R.id.edit_text_post);
        postButton = (Button) rootView.findViewById(R.id.edit_text_post_button);
        firebaseAuth= FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            getActivity().finish();
            startActivity(new Intent(getActivity().getApplicationContext(),LoginActivity.class));
        }


        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String post = editTextPost.getText().toString();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = user.getUid();
                NewPost newpost = new NewPost(uid,post);

                databaseReference.child("Post").push().setValue(newpost);
                Toast.makeText(getActivity().getApplicationContext(),"posting",Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }
}