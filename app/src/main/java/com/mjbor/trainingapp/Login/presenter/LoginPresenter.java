package com.mjbor.trainingapp.Login.presenter;


import com.mjbor.trainingapp.Login.model.LoginInteractor;
import com.mjbor.trainingapp.Login.view.ILoginView;
import com.mjbor.trainingapp.Register.model.RegisterInteractor;
import com.mjbor.trainingapp.Register.model.RegisterModel;
import com.mjbor.trainingapp.Utils.UserUtils;
import com.mjbor.trainingapp.models.Exercise;
import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.models.User;
import com.mjbor.trainingapp.sessions.ISessionManager;
import com.mjbor.trainingapp.sessions.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mjbor on 8/17/2017.
 */

public class LoginPresenter {

    private ILoginView view;
    private LoginInteractor loginInteractor;
    private ISessionManager sessionManager;

    private User user;

    private boolean requestSent;

    public LoginPresenter(ILoginView view, ISessionManager sessionManager){
        this.view = view;
        this.loginInteractor = new LoginInteractor(this);
        this.sessionManager = sessionManager;

        checkIfUserIsAlreadyLoggedIn();
    }


    public void onRegisterSuccess(){
        onFacebookLoginSuccess(user);
    }

    public void onRegisterFailure(){
        view.toast("Hmmm, some strange error appeared.");
    }


    public void onLoginSuccess(String message, String token){
        sessionManager.createLoginSession(token);
        requestSent = false;
        view.goToMain();
    }

    public void onLoginSuccess(String message){
        requestSent = false;
        view.goToMain();
    }


    public void onLoginFailure(String message){
        requestSent = false;
        view.setProgressBarInvisible();
        view.setButtonText("LOGIN");
        view.toast(message);
    }


    public void facebookUserDoesNotExist(){
        //show prompt with training history
        //TODO
        view.promptRecordPopup();
    }

    public void facebookUserExists(String token){
        onLoginSuccess("success facebook login", token);
    }

    public void userHistoryProvided(List<Double> records){
        List<Exercise> exercises1 = new ArrayList<>();
        List<Exercise> exercises2 = new ArrayList<>();

        records = generateFirstTrainingValues(records);

        String[] exercisesArray = {"Squat", "Bench press", "Barbell row", "Over head press", "Deadlift"};

        exercises1.add(new Exercise(new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5)) , records.get(0), "Squat", 5));
        exercises1.add(new Exercise(new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5)) , records.get(1), "Bench press", 5));
        exercises1.add(new Exercise(new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5)) , records.get(2), "Barbell row",  5));

        exercises2.add(new Exercise(new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5)) , records.get(1), "Squat",  5));
        exercises2.add(new Exercise(new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5)) , records.get(3), "Over head press", 5));
        exercises2.add(new Exercise(new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5)) , records.get(4), "Deadlift", 5));


        Training training1 = new Training();
        training1.setExercises(exercises1);


        Training training2 = new Training();
        training2.setExercises(exercises2);



        RegisterModel registerModel = new RegisterModel(training1, training2, user);

        loginInteractor.register(registerModel);
        view.setButtonText("");
        view.setProgressBarVisible();

    }

    public List<Double> generateFirstTrainingValues(List<Double> list){
        double dividedAndRounded;
        List<Double> result = new ArrayList<>();

        for(double d : list){
            d = d/2;
            dividedAndRounded = (int)(d/2.5);
            dividedAndRounded *= 2.5;
            result.add(dividedAndRounded);
        }

        return result;
    }



    public void onFacebookLoginSuccess(User user){
        this.user = user;
        fetchingData();
        loginInteractor.isFacebookUserRegistered(user.getEmail());
    }

    public void fetchingData(){
        view.setButtonText("");
        view.setProgressBarVisible();
    }

    public void login(String email, String password){
        if(!requestSent && validate(email, password)){
            view.setButtonText("");
            view.setProgressBarVisible();

            loginInteractor.login(email, password);
            requestSent = true;
        }
    }

    private void checkIfUserIsAlreadyLoggedIn(){
        if(sessionManager.isLoggedIn()){
            onLoginSuccess("User session held");
        }
    }

    private boolean validate(String email, String password){

        if(email.equals("")){
            view.toast("Email field can't be empty.");
            return false;
        }

        if(password.equals("")){
            view.toast("Password field can't be empty.");
            return false;
        }

        return true;
    }

}
