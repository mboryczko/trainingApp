package com.mjbor.trainingapp.Training.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mjbor.trainingapp.R;

public class TrainingActivity extends AppCompatActivity implements ITrainingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
    }



}
