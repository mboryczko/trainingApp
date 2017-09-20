package com.mjbor.trainingapp.Training.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.models.Exercise;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainingActivity extends AppCompatActivity implements ITrainingActivity {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

    //TODO
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise(new String[]{"5", "5", "5", "5", "5"} , "95 kg", "Bench Press"));
        exercises.add(new Exercise(new String[]{"6", "5", "5"}, "20 kg", "Chinups"));

        adapter = new TrainingAdapter(exercises, this);
        recyclerView.setAdapter(adapter);


    }



}
