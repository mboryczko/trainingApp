package com.mjbor.trainingapp.Main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by mjbor on 9/2/2017.
 */

public interface IMainView {

    public void toast(String message);

    public void setUpSpecificFragment(Fragment fragment, Bundle bundle);
    public void onLogout();
}
