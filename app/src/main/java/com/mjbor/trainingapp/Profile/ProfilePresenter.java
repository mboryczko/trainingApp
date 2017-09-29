package com.mjbor.trainingapp.Profile;

import android.widget.Button;

import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.models.Exercise;
import com.mjbor.trainingapp.models.User;
import com.mjbor.trainingapp.models.UserResponse;
import com.mjbor.trainingapp.sessions.ISessionManager;

import butterknife.BindView;

/**
 * Created by mjbor on 9/17/2017.
 */

public class ProfilePresenter {

    IProfileFragment view;
    ProfileInteractor interactor;
    String token;


    public ProfilePresenter(IProfileFragment view, String token){
        this.view = view;
        this.interactor = new ProfileInteractor(this);
        this.token = token;
        getUserInfo();
    }

    public void getUserInfo(){
        interactor.getUserInfo(token);
    }


    public void onUserDataFetchedSuccessfully(UserResponse u){
        view.setProfileUsername(u.getUsername());
        view.setProfileName(u.getName());
        view.setProfileSurname(u.getSurname());
        view.setProfileEmail(u.getEmail());
        view.setBestResults(prepareListOfBestResults(u.getExercises()));

        view.setCollapsingTootlbarTitle(u.getUsername());

    }


    public String prepareListOfBestResults(Exercise[] exercises){
        String list = "";
        for(Exercise e : exercises){
            list += "• " + e.getName() + " - " +  e.getWeight() + "\n";
        }

        return list;
    }

    public void onUserDataFetchedFailed(String serverMssage){

    }







}
