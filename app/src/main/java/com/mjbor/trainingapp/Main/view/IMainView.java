package com.mjbor.trainingapp.Main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mjbor.trainingapp.models.Training;

/**
 * Created by mjbor on 9/2/2017.
 */

public interface IMainView {

    public void toast(String message);

    //public void setUpSpecificFragment(Fragment fragment, Bundle bundle);
    public void onLogout();

    public void openLastTraining(Training training);

    public void killApp();
}
