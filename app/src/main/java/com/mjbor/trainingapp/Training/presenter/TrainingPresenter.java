package com.mjbor.trainingapp.Training.presenter;


import android.os.Handler;
import android.widget.Toast;

import com.mjbor.trainingapp.Training.model.TrainingInteractor;
import com.mjbor.trainingapp.Training.view.ITrainingView;
import com.mjbor.trainingapp.Utils.Constants;
import com.mjbor.trainingapp.Utils.StringUtils;
import com.mjbor.trainingapp.models.Exercise;
import com.mjbor.trainingapp.models.SetEvent;
import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.sessions.ISessionManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by mjbor on 9/18/2017.
 */

public class TrainingPresenter {


    private boolean requestSent;

    private ITrainingView view;
    private TrainingInteractor interactor;
    private ISessionManager sessionManager;
    private String token;
    private String additionalInfo;

    public TrainingPresenter(ITrainingView view, ISessionManager sessionManager){
        this.view = view;
        this.interactor = new TrainingInteractor(this);
        this.sessionManager = sessionManager;
        this.token = sessionManager.getUserToken();


        view.startTimer();
        view.setTimeAndDateInvisible();
        view.setInfoInvisible();

        getNextTraining();
    }


    public TrainingPresenter(ITrainingView view, ISessionManager sessionManager, Training training){
        this.view = view;
        this.interactor = new TrainingInteractor(this);
        this.sessionManager = sessionManager;
        this.token = sessionManager.getUserToken();

        view.setFloatingButtonInvisible();
        view.setTimeAndDateVisible();
        view.setInfoInvisible();

        showTraining(training);
    }


    public void onWeightClicked(double weight){
        view.promptPlateDialog(weight);
    }

    public void retryClicked(){
        getNextTraining();
    }

    public void getNextTraining(){
        view.setRetryButtonInvisible();
        view.setFailedInformationInvisible();
        view.setButtonInvisible();
        view.setFloatingButtonInvisible();
        view.setProgressBarCenterVisible();
        interactor.getNextTraining(token);
    }

    public void showTraining(Training training){

        view.setTimeAndDateText(training.getTraining_date());
        view.setProgressBarCenterInvisible();
        view.setProgressBarInvisible();
        view.setButtonVisible();
        view.showTraining(training);
    }


    public void onNextTrainingFetched(Training training){
        view.setProgressBarCenterInvisible();
        view.setFloatingButtonVisible();
        view.setButtonVisible();
        view.setTimeAndDateText(training.getTraining_date());
        view.showTraining(training);
    }

    public void onNextTrainingFailed(String message){
        view.setRetryButtonVisible();
        view.setProgressBarCenterInvisible();
        view.setFailedInformation(message);

    }

    public void onSetCompleted(SetEvent event){
        view.setTimeAndDateVisible();
        view.resetTimer();
        view.setInfoVisible();


        view.setInfoText(event.getMessage() +
                "\n" + Constants.REST_INFO);
    }

    public void updateTraining(Training training){
        if(validateTraining(training)){
            if(!requestSent){
                view.setButtonText("");
                view.setProgressBarVisible();
                training.setToken(sessionManager.getUserToken());

                interactor.updateTraining(training);

                requestSent = true;
            }
        }

        else {
            view.toast("Please provide all data");
        }
    }


    //should be triggered every second
    public void calculateTime(int seconds){
        int timeMinutes, timeSeconds;


        timeMinutes = (int)(seconds/60);
        timeSeconds = seconds % 60;

        String timeToShow = StringUtils.formatNumber(timeMinutes) + ":" + StringUtils.formatNumber(timeSeconds);


        view.setTimeAndDateText(timeToShow);
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
