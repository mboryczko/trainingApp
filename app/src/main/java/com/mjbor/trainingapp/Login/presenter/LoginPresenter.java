package com.mjbor.trainingapp.Login.presenter;


import com.mjbor.trainingapp.Login.model.LoginInteractor;
import com.mjbor.trainingapp.Login.view.ILoginView;
import com.mjbor.trainingapp.models.User;
import com.mjbor.trainingapp.sessions.ISessionManager;
import com.mjbor.trainingapp.sessions.SessionManager;

/**
 * Created by mjbor on 8/17/2017.
 */

public class LoginPresenter {

    private ILoginView view;
    private LoginInteractor loginInteractor;
    private ISessionManager sessionManager;

    private boolean requestSent;

    public LoginPresenter(ILoginView view, ISessionManager sessionManager){
        this.view = view;
        this.loginInteractor = new LoginInteractor(this);
        this.sessionManager = sessionManager;

        checkIfUserIsAlreadyLoggedIn();
    }


    public void onLoginSuccess(String message){
        sessionManager.createLoginSession("user1", "email1");
        requestSent = false;
        view.goToMain();
    }

    public void onLoginFailure(String message){
        requestSent = false;
        view.toast(message);
    }



    public void login(String username, String password){
        if(!requestSent && validate(username, password)){
            loginInteractor.login(username, password);
            requestSent = true;

        }

    }

    private void checkIfUserIsAlreadyLoggedIn(){
        if(sessionManager.isLoggedIn()){
            onLoginSuccess("User session held");
        }
    }

    private boolean validate(String username, String password){

        if(username.equals("")){
            view.toast("Username field can't be empty.");
            return false;
        }

        if(password.equals("")){
            view.toast("Password field can't be empty.");
            return false;
        }

        return true;
    }

}
