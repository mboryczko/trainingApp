package com.mjbor.trainingapp.Profile;

import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by mjbor on 9/18/2017.
 */

public interface ProfileWebService {

    @FormUrlEncoded
    @POST("getUserInfo.php")
    Call<UserResponse> getUserInfo(@Field("token") String token);

    @FormUrlEncoded
    @POST("getUserInfo.php")
    Call<Training> getUserBestResults(@Field("token") String token);

}
