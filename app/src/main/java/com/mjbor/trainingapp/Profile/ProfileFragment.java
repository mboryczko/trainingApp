package com.mjbor.trainingapp.Profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mjbor.trainingapp.Login.view.LoginActivity;
import com.mjbor.trainingapp.Main.view.MainActivity;
import com.mjbor.trainingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements IProfileFragment{

    private ProfilePresenter presenter;

    public ProfileFragment() {
        this.presenter = new ProfilePresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }



}
