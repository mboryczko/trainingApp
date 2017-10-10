package com.mjbor.trainingapp.Progress;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.mjbor.trainingapp.Profile.ProfilePresenter;
import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Utils.Constants;
import com.mjbor.trainingapp.models.ChartPoint;
import com.mjbor.trainingapp.models.ChartResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFragment extends Fragment
implements IProgressFragment{

    private LineChart chart;

    public ProgressPresenter presenter;

    public ProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        String token = getArguments().getString(Constants.TOKEN, null);
        this.presenter = new ProgressPresenter(this, token);

    }

    /*
        data: date - weight, date - weight, date - weight ...




     */



    @Override
    public void showChart(ChartResponse chartResponse) {
        List<ChartPoint> squatData = chartResponse.getSquat();


        final HashMap<Integer, String> numMap = new HashMap<>();
        List<Entry> entries = new ArrayList<Entry>();

        Date firstDate = chartResponse.getSquat().get(0).getTraining_date();

        for(int i=0; i<squatData.size(); i++){
            ChartPoint chartPoint = squatData.get(i);
            Date currentDate = chartPoint.getTraining_date();
            int daysBetween = getDifferenceDays(firstDate, currentDate);

            String pattern = "MMM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
            String date = simpleDateFormat.format(chartPoint.getTraining_date());

            numMap.put(daysBetween, date);
            entries.add(new Entry(daysBetween, squatData.get(i).getWeight()));

        }

        LineDataSet dataSet = new LineDataSet(entries, "Squat");


        dataSet.setColor(Color.GREEN);
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setLineWidth(2.0f);

        LineData data = new LineData(dataSet);
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                String text = numMap.get((int)value);
                return text != null ? text : "";
            }

        });


        chart.setData(data);
        chart.invalidate();

    }

    public static int getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public void showAllCharts(ChartResponse chartResponse) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_progress, container, false);
        ButterKnife.bind(this, view);

        chart = view.findViewById(R.id.chart);


        /*
        List<Entry> entries = new ArrayList<Entry>();

        for (int i=0; i<10; i++) {

            // turn your data into Entry objects
            entries.add(new Entry(i, i*i));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(Color.YELLOW);
        dataSet.setValueTextColor(Color.BLACK); // s

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh*/

        return view;
    }



}
