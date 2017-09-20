package com.mjbor.trainingapp.Training.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mjbor.trainingapp.Login.presenter.LoginPresenter;
import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Training.presenter.TrainingPresenter;
import com.mjbor.trainingapp.models.Exercise;
import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.sessions.ISessionManager;
import com.mjbor.trainingapp.sessions.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainingActivity extends AppCompatActivity implements ITrainingView {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Exercise> exercises;

    private TrainingPresenter presenter;
    private ISessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        ButterKnife.bind(this);

        session = new SessionManager(this);
        presenter = new TrainingPresenter(this, session);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

    //TODO
        exercises = new ArrayList<>();
        exercises.add(new Exercise(new String[]{"5", "5", "5", "5", "5"} , "95 kg", "Bench Press"));
        exercises.add(new Exercise(new String[]{"5", "5", "5", "5", "5"} , "120 kg", "Squat"));
        exercises.add(new Exercise(new String[]{"6", "6", "6"}, "20 kg", "Chinups"));

        adapter = new TrainingAdapter(exercises, this);
        recyclerView.setAdapter(adapter);




    }


    public void saveClicked(View v){
        Training training = new Training();
        Exercise[] tab = exercises.toArray(new Exercise[exercises.size()]);

        training.setExercises(tab);

        presenter.saveTraining(training);

    }



}
