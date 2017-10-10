package com.mjbor.trainingapp.Training.model;

import android.util.Log;

import com.mjbor.trainingapp.Login.model.LoginWebService;
import com.mjbor.trainingapp.Login.presenter.LoginPresenter;
import com.mjbor.trainingapp.Training.presenter.TrainingPresenter;
import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.rest.ApiClient;
import com.mjbor.trainingapp.rest.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mjbor on 9/18/2017.
 */

public class TrainingInteractor implements Callback<DefaultResponse> {

    private TrainingPresenter presenter;
    private TrainingWebService webService;

    public TrainingInteractor(TrainingPresenter presenter){
        this.presenter = presenter;
        webService = ApiClient.getClient().create(TrainingWebService.class);
    }

    public void saveTraining(Training training){
        Call<DefaultResponse> call = webService.createTraining(training);
        call.enqueue(this);
    }

    public void updateTraining(Training training){
        Call<DefaultResponse> call = webService.updateTraining(training);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                presenter.onTrainingSavedSuccessfully(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                presenter.onTrainingSavedFailed(t.getMessage());
            }
        });
    }

    public void getNextTraining(String token){
        Call<Training> call = webService.getNextTraining(token);
        call.enqueue(new Callback<Training>() {
            @Override
            public void onResponse(Call<Training> call, Response<Training> response) {
                Training training = response.body();
                if(training.isError()){
                    //user has no training history
                    //presenter.noTrainigHistory();
                    presenter.onNextTrainingFailed(training.getMessage());
                }

                else{
                    presenter.onNextTrainingFetched(training);
                }

            }

            @Override
            public void onFailure(Call<Training> call, Throwable t) {
                presenter.onNextTrainingFailed("Error connecting to server. Please try again later");
                Log.e(this.getClass().toString(), "failed call");
            }
        });
    }


    @Override
    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
        DefaultResponse userResponse  = response.body();

        if(userResponse.isError()){
            presenter.onTrainingSavedFailed(userResponse.getMessage());
        }

        else{
            presenter.onTrainingSavedSuccessfully(userResponse.getMessage());
        }

        Log.e("xd", "success");
        Log.e("xd", "success");

    }

    @Override
    public void onFailure(Call<DefaultResponse> call, Throwable t) {

        presenter.onTrainingSavedFailed("Oops something wen wrong. Please try again later");
        Log.e("xd", "fail");
        Log.e("xd", "fail");
    }
}
