package com.mjbor.trainingapp.Home;

import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.rest.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mjbor on 9/19/2017.
 */

public class HomeInteractor implements Callback<Training> {


    private HomePresenter presenter;
    private HomeWebService webService;

    public HomeInteractor(HomePresenter presenter){
        this.presenter = presenter;
        webService = ApiClient.getClient().create(HomeWebService.class);
    }

    public void getLastTrainingInfo(String token){
        Call<Training> call = webService.getLastTraining(token);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Training> call, Response<Training> response) {
        Training userResponse  = response.body();

        if(userResponse.isError()){
            presenter.onNoTrainingFetched(userResponse);
        }

        else{
            presenter.onTrainingFetchedSuccesfully(userResponse);
        }


    }

    @Override
    public void onFailure(Call<Training> call, Throwable t) {

    }
}
