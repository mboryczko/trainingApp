package com.mjbor.trainingapp.Progress;

import android.util.Log;

import com.mjbor.trainingapp.Profile.ProfileWebService;
import com.mjbor.trainingapp.Utils.UserUtils;
import com.mjbor.trainingapp.models.AllChartPointsAndUserResponse;
import com.mjbor.trainingapp.models.AllChartResponse;
import com.mjbor.trainingapp.models.ChartPoint;
import com.mjbor.trainingapp.models.ChartResponse;
import com.mjbor.trainingapp.models.UserResponse;
import com.mjbor.trainingapp.rest.ApiClient;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mjbor on 10/3/2017.
 */

public class ProgressInteractor implements Callback<AllChartResponse> {


    private ProgressPresenter presenter;
    private ProgressWebService progressWebService;
    private ProfileWebService profileWebService;

    public ProgressInteractor(ProgressPresenter presenter){
        this.presenter = presenter;
        progressWebService = ApiClient.getRxClient().create(ProgressWebService.class);
        profileWebService = ApiClient.getRxClient().create(ProfileWebService.class);
    }

    public void getAllChartPoints(String token){
        Observable<AllChartResponse> allChartResponseObservable = progressWebService.getChartPoints(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<UserResponse> userResponseObservable = profileWebService.getUserInfo(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        /*Call<AllChartResponse> call = progressWebService.getChartPoints(token);
        call.enqueue(this);*/

        AllChartPointsAndUserResponse combined = Observable.zip(allChartResponseObservable, userResponseObservable, new Func2)

    }


    @Override
    public void onResponse(Call<AllChartResponse> call, Response<AllChartResponse> response) {
        AllChartResponse allChartResponse = response.body();
        //String serverMessage = allChartResponse.getList().get(0).getMessage();

        //TODO
       /* if(chartResponse.isError()){
            presenter.onUserDataFetchedFailed(serverMessage);
        }*/

        /*else{
            presenter.onAllUserDataFetchedSuccessfully(allChartResponse);
        }*/

        presenter.onAllUserDataFetchedSuccessfully(allChartResponse);
    }

    @Override
    public void onFailure(Call<AllChartResponse> call, Throwable t) {
        Log.e("ProgressInteractor", t.getMessage().toString());
    }
}
