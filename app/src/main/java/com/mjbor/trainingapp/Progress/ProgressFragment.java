package com.mjbor.trainingapp.Progress;


import android.Manifest;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.github.mikephil.charting.utils.FileUtils;
import com.mjbor.trainingapp.Profile.ProfilePresenter;
import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Utils.ColorUtils;
import com.mjbor.trainingapp.Utils.Constants;
import com.mjbor.trainingapp.Utils.DateUtils;
import com.mjbor.trainingapp.Utils.FilesUtils;
import com.mjbor.trainingapp.models.AllChartResponse;
import com.mjbor.trainingapp.models.ChartPoint;
import com.mjbor.trainingapp.models.ChartResponse;
import com.mjbor.trainingapp.models.SetEvent;
import com.mjbor.trainingapp.models.UserResponse;
import com.mjbor.trainingapp.pdfCreator.FirstPdf;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFragment extends Fragment
implements IProgressFragment,
        AdapterView.OnItemSelectedListener {


    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.chart) LineChart chart;
    @BindView(R.id.saveChart) ImageView saveChart;

    //private LineChart chart;
    private List<Entry> entries = new ArrayList<>();
    private LineDataSet dataSet;
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

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UserResponse event) {
        presenter.onUserProfileDataFetched(event);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        clearData();
        String exercise = (String)parent.getItemAtPosition(pos);
        presenter.onExerciseChoosen(exercise);
    }


    public void clearData(){
        entries = new ArrayList<>();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public boolean checkPermissions(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    @Override
    public void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);
    }

/*    @Override
    public void saveChart(String fileName) {
        presenter.saveClicked();
        //presenter iterates over AllChartResponses -> for each loads chart and saves it
        //String path = FilesUtils.getSaveFileDirectory();
        String pathToChart = FilesUtils.createFileInDirectory("/trainingAppCharts", fileName);
        if(chart.saveToPath(fileName, "/Download/trainingAppCharts")){
            showToast("Image saved sucessfully");
            savePdf(fileName);
        }

        else{
            showToast("Image failed to save.");
        }
    }*/

    @Override
    public Bitmap getChartBitmap() {
        return chart.getChartBitmap();
    }

    public void savePdf(String pdfName){
        FirstPdf.createPdf( pdfName);
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(),message, Toast.LENGTH_LONG).show();
    }

    public void loadCharts(AllChartResponse allChartResponse){
        List<ChartResponse> listOfResponses = allChartResponse.getList();
        List<List<Entry>> listOfEntries = new ArrayList<>();
        List<LineDataSet> listOfDataSet = new ArrayList<>();

        String pattern = "MMM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        final HashMap<Integer, String> numMap = new HashMap<>();
        Date currentFirstDate = listOfResponses.get(0).getExercise().get(0).getTraining_date();

        for(int j=0; j<listOfResponses.size(); j++){
            ChartResponse chartResponse = listOfResponses.get(j);
            List<ChartPoint> exerciseData = chartResponse.getExercise();
            listOfEntries.add(new ArrayList<Entry>());


            Date firstDate = chartResponse.getExercise().get(0).getTraining_date();

            if(currentFirstDate.after(firstDate)){
                currentFirstDate = firstDate;
            }

            for(int i=0; i<exerciseData.size(); i++){
                ChartPoint chartPoint = exerciseData.get(i);
                Date currentDate = chartPoint.getTraining_date();
                int daysBetween = DateUtils.getDifferenceDays(currentFirstDate, currentDate);


                String date = simpleDateFormat.format(chartPoint.getTraining_date());

                numMap.put(daysBetween, date);
                listOfEntries.get(j).add(new Entry(daysBetween, exerciseData.get(i).getWeight()));
            }

            Collections.sort(listOfEntries.get(j), new EntryXComparator());
            LineDataSet lineDataSet = new LineDataSet(listOfEntries.get(j), chartResponse.getExerciseName());
            lineDataSet.setColor(ColorUtils.getColor(j));
            listOfDataSet.add(lineDataSet);
        }


        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                String text = numMap.get((int)value);
                return text != null ? text : "";
            }

        });


        //LineData lineData = new LineData(listOfDataSet.get(0), listOfDataSet.get(1), listOfDataSet.get(2), listOfDataSet.get(3));
        LineData lineData = new LineData(listOfDataSet.toArray(new LineDataSet[listOfDataSet.size()]));
        chart.setData(lineData);
        //chart.invalidate();

    }

    @Override
    public void invalidateChart(){
        chart.invalidate();
    }

    @Override
    public void addEntry(float x, float y) {
        entries.add(new Entry(x, y));
    }

    @Override
    public void clearEntries() {
        entries.clear();
    }

    @Override
    public void addDataSet(String labelName) {
        Collections.sort(entries, new EntryXComparator());
        dataSet = new LineDataSet(entries, labelName);
    }

    @Override
    public void styleDataSet(String hexColorLine, String hexColorDot, float lineWidth) {
        dataSet.setColor(Color.parseColor(hexColorLine));
        dataSet.setCircleColor(Color.parseColor(hexColorDot));
        dataSet.setLineWidth(lineWidth);
    }


    @Override
    public void loadChart(ChartResponse chartResponse,  HashMap<Integer, String> map) {

        final HashMap<Integer, String> numMap = map;
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
        //chart.invalidate();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_progress, container, false);
        ButterKnife.bind(this, view);

        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.exercises_array, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(adapter);*/
        spinner.setOnItemSelectedListener(this);
        saveChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.saveClicked();
            }
        });

        return view;
    }



}
