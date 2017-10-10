package com.mjbor.trainingapp.Profile;

import com.mjbor.trainingapp.models.User;

/**
 * Created by mjbor on 9/17/2017.
 */

public interface IProfileFragment {

    public void setProfileTopName(String topName);
    public void setProfileSurname(String surname);
    public void setProfileEmail(String email);
    public void setProfileAsync(String url);
    public void setCoverAsync(String url);

    public void setBestResults(String text);

    public void setCollapsingTootlbarTitle(String title);

    public void refreshData();



}
