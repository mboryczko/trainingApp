package com.mjbor.trainingapp.TrainingShow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Training.plateCalculator.PlateCalculatorDialog;
import com.mjbor.trainingapp.Training.presenter.TrainingPresenter;
import com.mjbor.trainingapp.Training.view.TrainingAdapter;
import com.mjbor.trainingapp.Utils.Constants;
import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.sessions.ISessionManager;
import com.mjbor.trainingapp.sessions.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainingShow extends AppCompatActivity {


    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private Training training;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_show);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        showTraining();
    }


    public void showTraining() {
        training = (Training)getIntent().getSerializableExtra(Constants.TRAINING);
        adapter = new TrainingShowAdapter(training.getExercises(), this);
        recyclerView.setAdapter(adapter);

    }




}
