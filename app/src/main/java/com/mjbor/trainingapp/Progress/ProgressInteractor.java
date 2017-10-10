package com.mjbor.trainingapp.Progress;

import android.util.Log;

import com.mjbor.trainingapp.models.ChartResponse;
import com.mjbor.trainingapp.models.UserResponse;
import com.mjbor.trainingapp.rest.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mjbor on 10/3/2017.
 */

public class ProgressInteractor implements Callback<ChartResponse> {


    private ProgressPresenter presenter;
    private ProgressWebService webService;

    public ProgressInteractor(ProgressPresenter presenter){
        this.presenter = presenter;
        webService = ApiClient.getClient().create(ProgressWebService.class);
    }

    public void getAllChartPoints(String token){
        Call<ChartResponse> call = webService.getChartPoints(token);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ChartResponse> call, Response<ChartResponse> response) {
        ChartResponse chartResponse = response.body();
        String serverMessage = chartResponse.getMessage();

        if(chartResponse.isError()){
            presenter.onUserDataFetchedFailed(serverMessage);
        }

        else{
            presenter.onUserDataFetchedSuccessfully(chartResponse);
        }

    }

    @Override
    public void onFailure(Call<ChartResponse> call, Throwable t) {
        Log.e("ProgressInteractor", t.getMessage().toString());
    }
}
