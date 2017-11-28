package com.mjbor.trainingapp.Profile;

import android.util.Log;
import android.widget.Button;

import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Utils.StringUtils;
import com.mjbor.trainingapp.models.Exercise;
import com.mjbor.trainingapp.models.User;
import com.mjbor.trainingapp.models.UserResponse;
import com.mjbor.trainingapp.sessions.ISessionManager;

import java.util.List;

import butterknife.BindView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by mjbor on 9/17/2017.
 */

public class ProfilePresenter {

    IProfileFragment view;
    ProfileInteractor interactor;
    String token;

    private int steps;
    private int calories;


    public ProfilePresenter(IProfileFragment view, String token) {
        this.view = view;
        this.interactor = new ProfileInteractor(this);
        this.token = token;
        getUserInfo();
    }


    public void imageClicked(){
        if(view.checkPermissions()){
            view.openGallery();
        }
        else {
            view.requestPermissions();
        }
    }

    public void uploadImge(MultipartBody.Part fileToUpload, RequestBody filename){
        interactor.uploadFile(fileToUpload, filename, view.createTextRequestBody(token));
    }

    public void readPermissionGranted(){
        Log.e("ProfilePresente", "ReadPermissionsGranted");
    }

    public void onUploadSuccess(String avatarUrl){
        view.showToast("Success upload file");
        view.setProfileAsync(avatarUrl);

        interactor.getUserInfo(token);
    }

    public void onUploadFailure(String errorMessage){
        view.showToast(errorMessage);
    }

    public void onImageFetched() {

    }

    public void onImageFailed(){

    }

    public void onStepsChanged(int steps){
        this.steps = steps;
        calculateAndSetCalories(steps);
    }

    public void calculateAndSetCalories(int steps){
        calories  = steps / 20;
    }


    public void getUserInfo(){
        interactor.getUserInfo(token);
    }

    public String getStepsInfo(){
        return "• Steps: "  +  this.steps + "\n" +
                "• Calories: "  +  this.calories + "\n";
    }

    public void onUserDataFetchedSuccessfully(UserResponse u){
        view.setProfileTopName(u.getName());
        view.setProfileSurname(u.getSurname());
        view.setProfileEmail(u.getEmail());
        view.setBestResults(StringUtils.prepareListOfBestResults(u.getExercises()));
        view.setProfileAsync(u.getAvatar());
        view.setCoverAsync(u.getCover());

        view.setCollapsingTootlbarTitle(u.getName());
        view.setSteps(getStepsInfo());

    }




    public void onUserDataFetchedFailed(String serverMssage){
        view.setProfileSurname(serverMssage);
    }







}
