package com.mjbor.trainingapp.Register.presenter;

import com.mjbor.trainingapp.Register.model.RegisterInteractor;
import com.mjbor.trainingapp.Register.view.IRegisterView;
import com.mjbor.trainingapp.models.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mjbor on 8/17/2017.
 */

public class RegisterPresenter {

    private IRegisterView view;
    private RegisterInteractor registerInteractor;

    public RegisterPresenter(IRegisterView view){
        this.view = view;
        this.registerInteractor = new RegisterInteractor(this);
    }

    public void register(User user){
        if(validate(user)){
            registerInteractor.register(user);
        }

    }

    public boolean validate(User user){

        if(user.getUsername().length() < 5){
            view.toast("Username has to be at least 5 characters long");
            return false;
        }

        if(!validateEmail(user.getEmail())){
            view.toast("Invalid email address");
            return false;
        }

        if(user.getPassword().length() < 6){
            view.toast("Password has to be at least 6 characters long");
            return false;
        }

        if(user.getName().equals("")){
            view.toast("Name field can't be empty.");
            return false;
        }

        if(user.getSurname().equals("")){
            view.toast("Username field can't be empty.");
            return false;
        }


        return true;
    }



    public void onRegisterSuccess(String message){
        view.toast(message);
        view.onRegisterSuccess();
    }

    public void onRegisterFailure(String message){
        view.toast(message);
    }



    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
}
