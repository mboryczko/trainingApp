package com.mjbor.trainingapp.Main.view;

import android.support.v4.app.Fragment;

/**
 * Created by mjbor on 9/2/2017.
 */

public interface IMainView {

    public void toast(String message);

    public void setUpSpecificFragment(Fragment fragment);
    public void onLogout();
}
