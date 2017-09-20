package com.mjbor.trainingapp.Training.presenter;


import com.mjbor.trainingapp.Training.model.TrainingInteractor;
import com.mjbor.trainingapp.Training.view.ITrainingView;
import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.sessions.ISessionManager;

/**
 * Created by mjbor on 9/18/2017.
 */

public class TrainingPresenter {


    private ITrainingView view;
    private TrainingInteractor interactor;
    private ISessionManager sessionManager;


    public TrainingPresenter(ITrainingView view, ISessionManager sessionManager){
        this.view = view;
        this.interactor = new TrainingInteractor(this);
        this.sessionManager = sessionManager;


    }


    public void saveTraining(Training training){
        training.setToken(sessionManager.getUserToken());
        interactor.saveTraining(training);
    }
}
