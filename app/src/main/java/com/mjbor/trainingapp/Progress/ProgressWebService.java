package com.mjbor.trainingapp.Progress;

import com.mjbor.trainingapp.models.AllChartResponse;
import com.mjbor.trainingapp.models.ChartResponse;
import com.mjbor.trainingapp.models.Training;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by mjbor on 10/3/2017.
 */

public interface ProgressWebService {

    @FormUrlEncoded
    @POST("getChartPoints.php")
    Observable<AllChartResponse> getChartPoints(@Field("token") String token);


}
