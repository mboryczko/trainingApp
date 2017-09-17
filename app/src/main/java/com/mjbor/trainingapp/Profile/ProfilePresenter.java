package com.mjbor.trainingapp.Profile;

import android.widget.Button;

import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.sessions.ISessionManager;

import butterknife.BindView;

/**
 * Created by mjbor on 9/17/2017.
 */

public class ProfilePresenter {

    IProfileFragment view;


    @BindView(R.id.logoutButton) Button logoutButton;

    public ProfilePresenter(IProfileFragment view){
        this.view = view;
    }


}
