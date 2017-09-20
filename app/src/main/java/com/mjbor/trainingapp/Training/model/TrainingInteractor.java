package com.mjbor.trainingapp.Training.model;

import android.util.Log;

import com.mjbor.trainingapp.Login.model.LoginWebService;
import com.mjbor.trainingapp.Login.presenter.LoginPresenter;
import com.mjbor.trainingapp.Training.presenter.TrainingPresenter;
import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.rest.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mjbor on 9/18/2017.
 */

public class TrainingInteractor implements Callback<Training> {

    private TrainingPresenter presenter;
    private TrainingWebService webService;

    public TrainingInteractor(TrainingPresenter presenter){
        this.presenter = presenter;
        webService = ApiClient.getClient().create(TrainingWebService.class);
    }

    public void saveTraining(Training training){
        Call<Training> call = webService.createTraining(training);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Training> call, Response<Training> response) {
        Training userResponse  = response.body();

        Log.e("xd", "success");
        Log.e("xd", "success");

    }

    @Override
    public void onFailure(Call<Training> call, Throwable t) {

        Log.e("xd", "fail");
        Log.e("xd", "fail");
    }
}
