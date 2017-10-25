package com.mjbor.trainingapp.Progress;

import android.util.Log;

import com.mjbor.trainingapp.models.AllChartResponse;
import com.mjbor.trainingapp.models.ChartPoint;
import com.mjbor.trainingapp.models.ChartResponse;
import com.mjbor.trainingapp.models.UserResponse;
import com.mjbor.trainingapp.rest.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mjbor on 10/3/2017.
 */

public class ProgressInteractor implements Callback<AllChartResponse> {


    private ProgressPresenter presenter;
    private ProgressWebService webService;

    public ProgressInteractor(ProgressPresenter presenter){
        this.presenter = presenter;
        webService = ApiClient.getClient().create(ProgressWebService.class);
    }

    public void getAllChartPoints(String token){
        Call<AllChartResponse> call = webService.getChartPoints(token);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<AllChartResponse> call, Response<AllChartResponse> response) {
        AllChartResponse allChartResponse = response.body();
        //String serverMessage = allChartResponse.getList().get(0).getMessage();

        //TODO
       /* if(chartResponse.isError()){
            presenter.onUserDataFetchedFailed(serverMessage);
        }*/

        /*else{
            presenter.onAllUserDataFetchedSuccessfully(allChartResponse);
        }*/

        presenter.onAllUserDataFetchedSuccessfully(allChartResponse);
    }

    @Override
    public void onFailure(Call<AllChartResponse> call, Throwable t) {
        Log.e("ProgressInteractor", t.getMessage().toString());
    }
}
