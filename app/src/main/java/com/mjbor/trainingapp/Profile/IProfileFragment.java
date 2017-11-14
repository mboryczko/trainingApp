package com.mjbor.trainingapp.Profile;

import com.mjbor.trainingapp.models.User;

import okhttp3.RequestBody;

/**
 * Created by mjbor on 9/17/2017.
 */

public interface IProfileFragment {

    public void setProfileTopName(String topName);
    public void setProfileSurname(String surname);
    public void setProfileEmail(String email);
    public void setProfileAsync(String url);
    public void setCoverAsync(String url);
    void showToast(String message);
    void openGallery();
    boolean checkPermissions();
    void requestPermissions();
    RequestBody createTextRequestBody(String text);


    public void setBestResults(String text);

    public void setCollapsingTootlbarTitle(String title);

    public void refreshData();



}
