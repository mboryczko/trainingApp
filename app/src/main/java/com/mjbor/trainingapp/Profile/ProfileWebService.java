package com.mjbor.trainingapp.Profile;

import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.models.UserResponse;
import com.mjbor.trainingapp.rest.DefaultResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @Multipart
    @POST("uploadFile.php")
    Call<DefaultResponse> uploadFile(@Part("token") RequestBody token, @Part MultipartBody.Part file, @Part("name") RequestBody name);
}
