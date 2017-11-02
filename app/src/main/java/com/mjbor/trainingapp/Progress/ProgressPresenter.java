package com.mjbor.trainingapp.Progress;

import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.mjbor.trainingapp.Utils.Constants;
import com.mjbor.trainingapp.Utils.DateUtils;
import com.mjbor.trainingapp.models.AllChartResponse;
import com.mjbor.trainingapp.models.ChartPoint;
import com.mjbor.trainingapp.models.ChartResponse;
import com.mjbor.trainingapp.models.PdfWrapper;
import com.mjbor.trainingapp.models.UserResponse;
import com.mjbor.trainingapp.pdfCreator.PdfGenerator;

import org.reactivestreams.Subscriber;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mjbor on 10/3/2017.
 */

public class ProgressPresenter {

    private IProgressFragment view;
    private ProgressInteractor interactor;
    private String exerciseChoosen;
    private String token;
    private boolean permissionGranted;
    private boolean progressDataFetched;
    private boolean profileDataFetched;

    private List<Bitmap> listOfChartsBitmaps;
    private AllChartResponse allChartResponse;
    private UserResponse userResponse;


    public ProgressPresenter(IProgressFragment view, String token){
        this.view = view;
        this.interactor = new ProgressInteractor(this);
        this.token = token;

        interactor.getAllChartPoints(token);
    }

    public void saveClicked(){
        permissionGranted = view.checkPermissions();
         listOfChartsBitmaps = new ArrayList<>();
        if(permissionGranted && progressDataFetched && profileDataFetched){
            Date date = new Date();
            String fileName = date.toString();
            List<ChartResponse> listOfChartResponses = allChartResponse.getList();

            //load profile image
            Observable.just(userResponse.getAvatar())
                    .subscribeOn(Schedulers.computation())
                    .map(url -> view.getProfilePicture(url))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Bitmap>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Bitmap bitmap) {
                            listOfChartsBitmaps.add(bitmap);

                            //load first chart with all exercises
                            view.clearEntries();
                            view.loadCharts(allChartResponse);
                            addCurrentChartToBitmapList();

                            //load all other exercises
                            for(ChartResponse chartResponse : listOfChartResponses){
                                view.clearEntries();
                                loadSpecificExerciseChart(chartResponse);
                                addCurrentChartToBitmapList();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                            //prepare classes
                            PdfWrapper pdfWrapper = new PdfWrapper(fileName, userResponse, listOfChartsBitmaps);
                            PdfGenerator pdfGenerator = new PdfGenerator();

                            //reactively create pdf raport
                            Observable.just(pdfWrapper)
                                    .subscribeOn(Schedulers.computation())
                                    .map(pdf -> pdfGenerator.createPdf(pdfWrapper))
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(message -> view.showToast(message));
                        }
                    });

        }else {
            //view.requestPermissions();
            view.showToast("You can't do that now :<");
        }

    }

    public void addCurrentChartToBitmapList(){
        Bitmap b = view.getChartBitmap();
        listOfChartsBitmaps.add(b);
    }

    public void storagePermissionGranted(){
        saveClicked();
    }


    public void onExerciseChoosen(String exerciseName){
        this.exerciseChoosen = exerciseName;

        if(exerciseName.equals("All")){
            if(progressDataFetched){
                view.loadCharts(allChartResponse);
                view.invalidateChart();
            }
        }

        else {
            if(progressDataFetched){
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
        progressDataFetched = true;
        view.loadCharts(allChartResponse);
        view.invalidateChart();
    }

    public void onUserProfileDataFetched(UserResponse userResponse){
        this.userResponse = userResponse;
        profileDataFetched = true;

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
