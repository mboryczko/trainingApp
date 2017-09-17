package com.mjbor.trainingapp.Login.model;

import com.mjbor.trainingapp.Login.presenter.LoginPresenter;
import com.mjbor.trainingapp.models.User;
import com.mjbor.trainingapp.rest.ApiClient;
import com.mjbor.trainingapp.rest.DefaultResponse;
import com.mjbor.trainingapp.rest.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mjbor on 8/17/2017.
 */

public class LoginInteractor implements Callback<LoginResponse> {

    private LoginPresenter loginPresenter;
    private LoginWebService loginWebService;

    public LoginInteractor(LoginPresenter loginPresenter){
        this.loginPresenter = loginPresenter;
        loginWebService = ApiClient.getClient().create(LoginWebService.class);
    }


    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        LoginResponse loginResponse  = response.body();
        String serverMessage = loginResponse.getMessage();

        if(loginResponse.isError()){
            loginPresenter.onLoginFailure(serverMessage);
        }

        else{
            String token = loginResponse.getToken();
            loginPresenter.onLoginSuccess(serverMessage, token);
        }


    }

    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {
        loginPresenter.onLoginFailure("Error connecting to server");
    }

    public void login(String username, String password){
        Call<LoginResponse> call = loginWebService.login(username, password);
        call.enqueue(this);
    }

}
