package com.mjbor.trainingapp.Profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mjbor.trainingapp.Login.view.LoginActivity;
import com.mjbor.trainingapp.Main.view.MainActivity;
import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Utils.Constants;
import com.mjbor.trainingapp.models.User;

import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements IProfileFragment{

    private ProfilePresenter presenter;

    @BindView(R.id.userNameTextView) TextView usernameTextView;
    @BindView(R.id.nameTextView) TextView nameTextView;
    @BindView(R.id.surnameTextView) TextView surnameTextView;
    @BindView(R.id.emailTextView) TextView emailTextView;
    @BindView(R.id.profileImage) FloatingActionButton floatingActionButton;

    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;



    public ProfileFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        String token = getArguments().getString(Constants.TOKEN, null);
        this.presenter = new ProfilePresenter(this, token);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(Constants.USERNAME, usernameTextView.getText().toString());
        outState.putString(Constants.NAME, nameTextView.getText().toString());
        outState.putString(Constants.SURNAME, surnameTextView.getText().toString());
        outState.putString(Constants.EMAIL, emailTextView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        if(savedInstanceState != null){
            setCollapsingTootlbarTitle(Constants.USERNAME);
            usernameTextView.setText(savedInstanceState.getString(Constants.USERNAME));
            nameTextView.setText(savedInstanceState.getString(Constants.NAME));
            surnameTextView.setText(savedInstanceState.getString(Constants.SURNAME));
            emailTextView.setText(savedInstanceState.getString(Constants.EMAIL));
        }




        return view;
    }


    @Override
    public void setCollapsingTootlbarTitle(String title) {
        collapsingToolbarLayout.setTitle(title);
    }

    @Override
    public void setProfileUsername(String username) {
        usernameTextView.setText(username);
    }

    @Override
    public void setProfileName(String name) {
        nameTextView.setText(name);
    }

    @Override
    public void setProfileSurname(String surname) {
        surnameTextView.setText(surname);
    }

    @Override
    public void setProfileEmail(String email) {
        emailTextView.setText(email);
    }


}
