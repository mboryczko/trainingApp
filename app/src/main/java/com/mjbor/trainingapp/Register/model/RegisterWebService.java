package com.mjbor.trainingapp.Register.model;

import com.mjbor.trainingapp.rest.DefaultResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by mjbor on 8/17/2017.
 */

public interface RegisterWebService {

    @POST("/newRegister.php")
    Call<DefaultResponse> registration(@Body RegisterModel registerModel);

}
