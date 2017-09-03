package com.mjbor.trainingapp.Login.model;

import com.mjbor.trainingapp.Login.presenter.LoginPresenter;
import com.mjbor.trainingapp.models.User;
import com.mjbor.trainingapp.rest.ApiClient;
import com.mjbor.trainingapp.rest.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mjbor on 8/17/2017.
 */

public class LoginInteractor implements Callback<DefaultResponse> {

    private LoginPresenter loginPresenter;
    private LoginWebService loginWebService;

    public LoginInteractor(LoginPresenter loginPresenter){
        this.loginPresenter = loginPresenter;

        loginWebService = ApiClient.getClient().create(LoginWebService.class);
    }


    @Override
    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
        DefaultResponse defaultResponse  = response.body();
        String serverMessage = defaultResponse.getMessage();

        if(defaultResponse.isError()){
            loginPresenter.onLoginFailure(serverMessage);
        }

        else{
            loginPresenter.onLoginSuccess(serverMessage);
        }


    }

    @Override
    public void onFailure(Call<DefaultResponse> call, Throwable t) {
        loginPresenter.onLoginFailure("Error connecting to server");
    }

    public void login(String username, String password){
        Call<DefaultResponse> call = loginWebService.login(username, password);
        call.enqueue(this);
    }

}
