package com.mjbor.trainingapp.Profile;

import android.util.Log;

import com.mjbor.trainingapp.models.UserResponse;
import com.mjbor.trainingapp.rest.ApiClient;
import com.mjbor.trainingapp.rest.DefaultResponse;

import org.greenrobot.eventbus.EventBus;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

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

        if(userResponse.getError()){
            presenter.onUserDataFetchedFailed(serverMessage);
        }

        else{
            presenter.onUserDataFetchedSuccessfully(userResponse);
            EventBus.getDefault().post(userResponse);
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

    public void uploadFile(MultipartBody.Part fileToUpload, RequestBody filename, RequestBody token){
        Call<DefaultResponse> fileUpload = webService.uploadFile(token, fileToUpload, token);
        fileUpload.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.body().isError()){
                    presenter.onUploadFailure(response.body().getMessage());
                }
                else {

                    presenter.onUploadSuccess(response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                presenter.onUploadFailure(t.getMessage().toString());
            }
        });
    }
}
