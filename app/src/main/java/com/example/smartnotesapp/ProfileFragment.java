package com.example.smartnotesapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class ProfileFragment extends Fragment {

    ImageButton editProfileImageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        editProfileImageButton = rootView.findViewById(R.id.edit_Profile_button);

        editProfileImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
                getActivity().finish();
            }
        });


        return rootView;
    }
}