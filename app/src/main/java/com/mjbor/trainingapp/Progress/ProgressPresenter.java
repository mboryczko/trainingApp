package com.mjbor.trainingapp.Progress;

import com.mjbor.trainingapp.models.ChartPoint;
import com.mjbor.trainingapp.models.ChartResponse;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mjbor on 10/3/2017.
 */

public class ProgressPresenter {

    IProgressFragment view;
    ProgressInteractor interactor;
    String token;


    public ProgressPresenter(IProgressFragment view, String token){
        this.view = view;
        this.interactor = new ProgressInteractor(this);
        this.token = token;

        getAllChartPoints();
    }


    public void getAllChartPoints(){
        interactor.getAllChartPoints(token);
    }


    public void onUserDataFetchedFailed(String serverMessage){

    }

    public void onUserDataFetchedSuccessfully(ChartResponse chartResponse){

        view.showChart(chartResponse);






        /*view.showAllCharts(chartResponse);

        List<Entry> entries = new ArrayList<>();

        for (int i=0; i<chartResponse.getSquat().size(); i++) {
            // turn your data into Entry objects
            ChartPoint chartPoint = chartResponse.getSquat().get(i);
            entries.add(new Entry(chartPoint.getTraining_date(), chartPoint.getWeight()));

        }
*/

    }
}
