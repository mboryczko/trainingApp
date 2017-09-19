package com.mjbor.trainingapp.Profile;

import com.mjbor.trainingapp.models.UserResponse;
import com.mjbor.trainingapp.rest.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mjbor on 9/18/2017.
 */

public class ProfileInteractor implements Callback<UserResponse> {


    private ProfilePresenter presenter;
    private ProfileWebService webService;

    public ProfileInteractor(ProfilePresenter presenter){
        this.presenter = presenter;
        webService = ApiClient.getClient().create(ProfileWebService.class);
    }


    @Override
    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
        UserResponse userResponse  = response.body();
        String serverMessage = userResponse.getMessage();

        if(userResponse.isError()){
            presenter.onUserDataFetchedFailed(serverMessage);
        }

        else{
            presenter.onUserDataFetchedSuccessfully(userResponse);
        }
    }

    @Override
    public void onFailure(Call<UserResponse> call, Throwable t) {
        presenter.onUserDataFetchedFailed("Error connecting to server");
    }

    public void getUserInfo(String token){
        Call<UserResponse> call = webService.getUserInfo(token);
        call.enqueue(this);
    }
}
