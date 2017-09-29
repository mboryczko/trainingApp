package com.mjbor.trainingapp.Profile;

import com.mjbor.trainingapp.models.User;

/**
 * Created by mjbor on 9/17/2017.
 */

public interface IProfileFragment {

    public void setProfileUsername(String username);
    public void setProfileName(String name);
    public void setProfileSurname(String surname);
    public void setProfileEmail(String email);

    public void setBestResults(String text);

    public void setCollapsingTootlbarTitle(String title);

    public void refreshData();



}
