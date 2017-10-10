package com.mjbor.trainingapp.Register.presenter;

import com.mjbor.trainingapp.Register.model.RegisterInteractor;
import com.mjbor.trainingapp.Register.model.RegisterModel;
import com.mjbor.trainingapp.Register.view.IRegisterView;
import com.mjbor.trainingapp.models.Exercise;
import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mjbor on 8/17/2017.
 */

public class RegisterPresenter {

    private IRegisterView view;
    private RegisterInteractor interactor;

    private User user;

    public RegisterPresenter(IRegisterView view){
        this.view = view;
        this.interactor = new RegisterInteractor(this);
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

        interactor.register(registerModel);
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

    public void registerClicked(User user){
        if(validate(user)){
            this.user = user;
            view.promptRecordPopup();
        }




/*        if(validate(user)){
            interactor.register(user);
            view.setButtonText("");
            view.setProgressBarVisible();
        }*/

    }



    public boolean validate(User user){

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
        view.setProgressBarInvisible();
        view.setButtonText("Login");
        view.toast(message);
    }



    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
}
