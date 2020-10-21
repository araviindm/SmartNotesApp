package com.example.smartnotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                UserInfo userProfile = dataSnapshot.child("Users").child(firebaseAuth.getUid()).getValue(UserInfo.class);

                User.name = userProfile.getUserName();
                User.surname = userProfile.getUserSurname();
                User.email = user.getEmail();
                User.phone = userProfile.getUserPhoneno();
                

            }
            @Override
            public void onCancelled( DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
            }
        });
        loadFragment(new HomeFragment());


        BottomNavigationView bottomNavigationView =  findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = null;

                        switch (item.getItemId()) {
                            case R.id.action_home:
                                fragment = new HomeFragment();
                                break;

                            case R.id.action_search:
                                fragment = new SearchFragment();
                                break;

                            case R.id.action_profile:
                                fragment = new ProfileFragment();
                                break;


                        }

                        return loadFragment(fragment);
                    }
                });
    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_post_button:
                startActivity(new Intent(getApplicationContext(), NewPost.class));
                return true;
            case R.id.edit_Profile:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onBackPressed(){
        finishAffinity();
    }
}
