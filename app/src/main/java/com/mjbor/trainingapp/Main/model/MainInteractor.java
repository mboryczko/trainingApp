package com.mjbor.trainingapp.Main.model;

import com.mjbor.trainingapp.Login.model.LoginWebService;
import com.mjbor.trainingapp.Login.presenter.LoginPresenter;
import com.mjbor.trainingapp.Main.presenter.MainPresenter;
import com.mjbor.trainingapp.rest.ApiClient;
import com.mjbor.trainingapp.rest.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mjbor on 8/17/2017.
 */

public class MainInteractor implements Callback<DefaultResponse> {

    private MainPresenter mainPresenter;
    private MainWebService mainWebService;

    public MainInteractor(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter;
        mainWebService = ApiClient.getClient().create(MainWebService.class);
    }


    @Override
    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
/*        DefaultResponse defaultResponse  = response.body();
        String serverMessage = defaultResponse.getMessage();

        if(defaultResponse.isError()){
            mainPresenter.onLoginFailure(serverMessage);
        }

        else{
            loginPresenter.onLoginSuccess(serverMessage);
        }*/


    }

    @Override
    public void onFailure(Call<DefaultResponse> call, Throwable t) {
        //loginPresenter.onLoginFailure("Error connecting to server");
    }

    /*public void login(String username, String password){
        Call<DefaultResponse> call = loginWebService.login(username, password);
        call.enqueue(this);
    }*/

}

