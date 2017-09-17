package com.mjbor.trainingapp.Main.presenter;

import android.app.Fragment;


import com.mjbor.trainingapp.Main.model.MainInteractor;
import com.mjbor.trainingapp.Main.view.IMainView;
import com.mjbor.trainingapp.sessions.ISessionManager;


/**
 * Created by mjbor on 8/17/2017.
 */

public class MainPresenter {

    private IMainView view;
    private MainInteractor mainInteractor;
    private ISessionManager sessionManager;

    private boolean requestSent;

    public MainPresenter(IMainView view, ISessionManager sessionManager){
        this.view = view;
        this.mainInteractor = new MainInteractor(this);
        this.sessionManager = sessionManager;

    }


}
