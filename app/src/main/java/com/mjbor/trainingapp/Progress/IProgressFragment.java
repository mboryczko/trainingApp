package com.mjbor.trainingapp.Progress;

import android.graphics.Bitmap;

import com.mjbor.trainingapp.models.AllChartResponse;
import com.mjbor.trainingapp.models.ChartResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by mjbor on 10/3/2017.
 */

public interface IProgressFragment {

    boolean checkPermissions();
    void requestPermissions();
/*    void saveChart(String exerciseName);*/
    void invalidateChart();

    void showToast(String message);

    void loadCharts(AllChartResponse allChartResponse);
    void clearEntries();

    Bitmap getProfilePicture(String url);

    void addEntry(float x, float y);
    void addDataSet(String labelName);
    void styleDataSet(String hexColorLine, String hexColorDot, float lineWidth);

    void loadChart(ChartResponse chartResponse, HashMap<Integer, String> map);
    Bitmap getChartBitmap();
}
