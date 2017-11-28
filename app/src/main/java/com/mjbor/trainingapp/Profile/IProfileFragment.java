package com.mjbor.trainingapp.Profile;

import com.mjbor.trainingapp.models.User;

import okhttp3.RequestBody;

/**
 * Created by mjbor on 9/17/2017.
 */

public interface IProfileFragment {

    void setProfileTopName(String topName);
    void setProfileSurname(String surname);
    void setProfileEmail(String email);
    void setProfileAsync(String url);
    void setCoverAsync(String url);
    void setSteps(String text);
    void showToast(String message);
    void openGallery();
    boolean checkPermissions();
    void requestPermissions();
    RequestBody createTextRequestBody(String text);


    public void setBestResults(String text);

    public void setCollapsingTootlbarTitle(String title);

    public void refreshData();



}
