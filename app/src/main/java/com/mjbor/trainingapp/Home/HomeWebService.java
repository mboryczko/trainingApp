package com.mjbor.trainingapp.Home;

import com.mjbor.trainingapp.models.Training;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by mjbor on 9/19/2017.
 */

public interface HomeWebService {

    @FormUrlEncoded
    @POST("getLastTraining.php")
    Call<Training> getLastTraining(@Field("token") String token);
}
