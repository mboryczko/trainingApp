package com.mjbor.trainingapp.Register.model;

import com.mjbor.trainingapp.Register.presenter.RegisterPresenter;
import com.mjbor.trainingapp.models.User;
import com.mjbor.trainingapp.rest.ApiClient;
import com.mjbor.trainingapp.rest.DefaultResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mjbor on 8/17/2017.
 */

public class RegisterInteractor implements Callback<DefaultResponse> {

    private RegisterPresenter presenter;
    private RegisterWebService registerWebService;

    public RegisterInteractor(RegisterPresenter registerPresenter) {
        this.presenter = registerPresenter;
        registerWebService = ApiClient.getClient().create(RegisterWebService.class);
    }


    public void register(User user) {
        Call<DefaultResponse> call = registerWebService.registration(user);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
        DefaultResponse defaultResponse  = response.body();
        String serverMessage = defaultResponse.getMessage();

        if(defaultResponse.isError()){
            presenter.onRegisterFailure(serverMessage);
        }

        else{
            presenter.onRegisterSuccess(serverMessage);
        }


    }

    @Override
    public void onFailure(Call<DefaultResponse> call, Throwable t) {
        presenter.onRegisterFailure("Error connecting to server");
    }

}

