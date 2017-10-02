package com.mjbor.trainingapp.Training.presenter;


import android.os.Handler;
import android.widget.Toast;

import com.mjbor.trainingapp.Training.model.TrainingInteractor;
import com.mjbor.trainingapp.Training.view.ITrainingView;
import com.mjbor.trainingapp.models.Exercise;
import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.sessions.ISessionManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mjbor on 9/18/2017.
 */

public class TrainingPresenter {


    private boolean requestSent;

    private ITrainingView view;
    private TrainingInteractor interactor;
    private ISessionManager sessionManager;
    private String token;


    public TrainingPresenter(ITrainingView view, ISessionManager sessionManager){
        this.view = view;
        this.interactor = new TrainingInteractor(this);
        this.sessionManager = sessionManager;
        this.token = sessionManager.getUserToken();

        getNextTraining();
    }


    public void onWeightClicked(double weight){
        view.promptPlateDialog(weight);
    }

    public void getNextTraining(){
        interactor.getNextTraining(token);
    }


    public void onNextTrainingFetched(Training training){
        view.showTraining(training);
    }


    public void saveTraining(Training training){
        if(validateTraining(training)){
            if(!requestSent){
                view.setButtonText("");
                view.setProgressBarVisible();
                training.setToken(sessionManager.getUserToken());

                interactor.saveTraining(training);

                requestSent = true;
            }
        }

        else {
            view.toast("Please provide all data");
        }



    }

    public boolean validateTraining(Training training){
        List<Exercise> exercises = training.getExercises();
        List<Integer> reps;

        for(int i=0; i<exercises.size(); i++){
            reps = exercises.get(i).getReps();
            for(int rep : reps){
                if(rep == -1 ){
                    return false;
                }
            }
        }

        return true;
    }


    public void onTrainingSavedSuccessfully(String message){
        requestSent = false;
        view.toast(message);
        view.goHomeWithoutStack();
    }

    public void onTrainingSavedFailed(String message){
        requestSent = false;
        view.setProgressBarInvisible();
        view.setButtonText("SAVE");
        view.toast(message);
    }



}
