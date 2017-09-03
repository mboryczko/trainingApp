package com.mjbor.trainingapp.Login.model;

import com.mjbor.trainingapp.models.User;
import com.mjbor.trainingapp.rest.DefaultResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by mjbor on 8/17/2017.
 */

public interface LoginWebService {
    @FormUrlEncoded
    @POST("login.php")
    Call<DefaultResponse> login(@Field("username") String username, @Field("password") String password);

}
