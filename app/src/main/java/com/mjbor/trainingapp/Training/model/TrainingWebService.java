package com.mjbor.trainingapp.Training.model;

import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.rest.DefaultResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by mjbor on 9/20/2017.
 */

public interface TrainingWebService {

    @POST("createTraining.php")
    Call<DefaultResponse> createTraining(@Body Training training);

    @POST("updateTraining.php")
    Call<DefaultResponse> updateTraining(@Body Training training);

    @FormUrlEncoded
    @POST("getNextTraining.php")
    Call<Training> getNextTraining(@Field("token") String token);



}
