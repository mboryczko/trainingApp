package com.mjbor.trainingapp.Progress;

import android.graphics.Bitmap;

import com.mjbor.trainingapp.Utils.Constants;
import com.mjbor.trainingapp.Utils.DateUtils;
import com.mjbor.trainingapp.models.AllChartResponse;
import com.mjbor.trainingapp.models.ChartPoint;
import com.mjbor.trainingapp.models.ChartResponse;
import com.mjbor.trainingapp.pdfCreator.FirstPdf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by mjbor on 10/3/2017.
 */

public class ProgressPresenter {

    private IProgressFragment view;
    private ProgressInteractor interactor;
    private String exerciseChoosen;
    private String token;
    private boolean permissionGranted;
    private boolean dataFetched;

    private List<Bitmap> listOfChartsBitmaps = new ArrayList<>();
    private AllChartResponse allChartResponse;


    public ProgressPresenter(IProgressFragment view, String token){
        this.view = view;
        this.interactor = new ProgressInteractor(this);
        this.token = token;

        interactor.getAllChartPoints(token);
    }

    public void saveClicked(){
        permissionGranted = view.checkPermissions();
        if(permissionGranted && dataFetched){
            Date date = new Date();
            String fileName = date.toString();
            List<ChartResponse> listOfChartResponses = allChartResponse.getList();
            for(ChartResponse chartResponse : listOfChartResponses){
                view.clearEntries();
                loadSpecificExerciseChart(chartResponse);
                Bitmap b = view.getChartBitmap();
                listOfChartsBitmaps.add(b);
            }

            if(FirstPdf.createPdf(fileName, listOfChartsBitmaps)){
                view.showToast("Report generated successfully");
            }

        }else {
            view.requestPermissions();
        }

    }

    public void storagePermissionGranted(){
        saveClicked();
    }


    public void onExerciseChoosen(String exerciseName){
        this.exerciseChoosen = exerciseName;

        if(exerciseName.equals("All")){
            if(dataFetched){
                onAllUserDataFetchedSuccessfully(allChartResponse);
            }
        }

        else {
            if(dataFetched){
                List<ChartResponse> listOfChartResponses = allChartResponse.getList();
                //TODO
                //find object in the list corresponding to the exerciseChoose
                //call onUserDataFetchedSuccessfully with this
                for(ChartResponse chartResponse : listOfChartResponses){
                    if(chartResponse.getExerciseName().equals(exerciseName)){
                        loadSpecificExerciseChart(chartResponse);
                        view.invalidateChart();

                    }
                }
            }
        }



    }



    public void onAllUserDataFetchedSuccessfully(AllChartResponse allChartResponse){
        this.allChartResponse = allChartResponse;
        dataFetched = true;
        view.loadCharts(allChartResponse);
        view.invalidateChart();
    }


    public void loadSpecificExerciseChart(ChartResponse chartResponse){

        List<ChartPoint> exerciseData = chartResponse.getExercise();
        HashMap<Integer, String> numMap = new HashMap<>();
        //(days from first date, DATE in proper format)
        //List<Entry> entries = new ArrayList<Entry>();

        Date firstDate = chartResponse.getExercise().get(0).getTraining_date();

        for(int i=0; i<exerciseData.size(); i++){
            ChartPoint chartPoint = exerciseData.get(i);
            Date currentDate = chartPoint.getTraining_date();
            int daysBetween = DateUtils.getDifferenceDays(firstDate, currentDate);

            String pattern = "MMM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
            String date = simpleDateFormat.format(chartPoint.getTraining_date());

            numMap.put(daysBetween, date);
            view.addEntry(daysBetween, exerciseData.get(i).getWeight());
        }

        view.addDataSet(chartResponse.getExerciseName());
        view.styleDataSet(Constants.COLOR3, Constants.COLOR3, 2.0f);
        view.loadChart(chartResponse, numMap);

    }

}
