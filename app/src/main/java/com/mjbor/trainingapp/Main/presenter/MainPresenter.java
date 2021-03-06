package com.mjbor.trainingapp.Main.presenter;

import android.app.Fragment;
import android.os.Handler;


import com.mjbor.trainingapp.Main.model.MainInteractor;
import com.mjbor.trainingapp.Main.view.IMainView;
import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.sessions.ISessionManager;


/**
 * Created by mjbor on 8/17/2017.
 */

public class MainPresenter {

    private Training lastTraining;

    private IMainView view;
    private MainInteractor mainInteractor;
    private ISessionManager sessionManager;
    private boolean doubleBackToExitPressedOnce = false;

    private boolean requestSent;

    public MainPresenter(IMainView view, ISessionManager sessionManager){
        this.view = view;
        this.mainInteractor = new MainInteractor(this);
        this.sessionManager = sessionManager;

    }

    public void logoutClicked(){
        sessionManager.logoutUser();
        view.onLogout();
    }

    public void gotLastTraining(Training training){
        this.lastTraining = training;
    }

    public void lastTrainingClicked(){
        view.openLastTraining(lastTraining);
    }

    public void onBackPressed(){
        if (doubleBackToExitPressedOnce) {
            view.killApp();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        view.toast("Please click BACK again to exit");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
