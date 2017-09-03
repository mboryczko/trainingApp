package com.mjbor.trainingapp.Register.model;

import com.mjbor.trainingapp.models.User;
import com.mjbor.trainingapp.rest.DefaultResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Created by mjbor on 8/17/2017.
 */

public interface RegisterWebService {

    @POST("/register.php")
    Call<DefaultResponse> registration(@Body User user);


}
