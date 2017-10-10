package com.mjbor.trainingapp.Login.model;

import com.mjbor.trainingapp.Register.model.RegisterModel;
import com.mjbor.trainingapp.models.User;
import com.mjbor.trainingapp.rest.DefaultResponse;
import com.mjbor.trainingapp.rest.LoginResponse;

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
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("facebookLogin.php")
    Call<LoginResponse> isFacebookUserRegistered(@Field("email") String email);

    @POST("/newRegister.php")
    Call<DefaultResponse> registration(@Body RegisterModel registerModel);

}
