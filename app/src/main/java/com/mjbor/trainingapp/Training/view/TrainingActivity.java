package com.mjbor.trainingapp.Training.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mjbor.trainingapp.Home.HomeFragment;
import com.mjbor.trainingapp.Main.view.MainActivity;
import com.mjbor.trainingapp.R;
import com.mjbor.trainingapp.Training.newExercise.NewExerciseDialog;
import com.mjbor.trainingapp.Training.plateCalculator.PlateCalculatorDialog;
import com.mjbor.trainingapp.Training.presenter.TrainingPresenter;
import com.mjbor.trainingapp.Utils.Constants;
import com.mjbor.trainingapp.models.Exercise;
import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.sessions.ISessionManager;
import com.mjbor.trainingapp.sessions.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainingActivity extends AppCompatActivity
        implements ITrainingView,
        PlateCalculatorDialog.PlateCalculatorListener,
        NewExerciseDialog.NewExerciseListener{

    @BindView(R.id.trainingDateTV) TextView trainingDateTV;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.progressBarTraining) ProgressBar progressBar;
    @BindView(R.id.saveTrainingButton) Button saveTrainingButton;
    @BindView(R.id.progressBar) ProgressBar progressBarCenter;
    @BindView(R.id.failInformation) TextView failInformation;
    @BindView(R.id.retryButton) Button retryButton;
    @BindView(R.id.floatingButton) FloatingActionButton floatingActionButton;

    private RecyclerView.Adapter adapter;
    private Training training;
    private PlateCalculatorDialog dialog;
    boolean trainingView;

    private TextView weightTV;

    private TrainingPresenter presenter;
    private ISessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        ButterKnife.bind(this);

        this.training = (Training)getIntent().getSerializableExtra(Constants.TRAINING);


        disableKeyboardOnStart();

        session = new SessionManager(this);
        if(training != null){
            //training only to view
            trainingView = true;
            presenter = new TrainingPresenter(this, session, training);
        }

        else
        {
            presenter = new TrainingPresenter(this, session);
        }

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

    }



    @Override
    public void onDialogPositiveCheck(DialogFragment dialog, Exercise exercise) {
        List<Exercise> exercises = training.getExercises();
        //exercises.add(new Exercise(new ArrayList<>(Arrays.asList(6,6,6)) , 20, "B", 6));
        exercises.add(exercise);

        adapter = new TrainingAdapter(exercises, this, trainingView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDialogPositiveCheck(DialogFragment dialog, double weight) {
        weightTV.setText(Double.toString(weight));
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void weightClicked(View v){
        this.weightTV = (TextView)v;
        double weight = Double.parseDouble(((TextView)v).getText().toString());
        promptPlateDialog(weight);
    }


    @Override
    public void promptPlateDialog(double weight) {
        dialog = new PlateCalculatorDialog();
        Bundle args = new Bundle();
        args.putDouble(Constants.WEIGHT, weight);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "PlateCalculatorDialog");
    }

    @Override
    public void showTraining(Training training) {
        this.training = training;
        trainingDateTV.setText(training.getTraining_date());
        adapter = new TrainingAdapter(training.getExercises(), this, trainingView);
        recyclerView.setAdapter(adapter);

    }

    private void disableKeyboardOnStart(){
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    public void saveClicked(View v){
        if(trainingView){
            presenter.updateTraining(training);
        }
        else {
            presenter.saveTraining(training);
        }

    }

    public void retryClick(View v){
        presenter.retryClicked();
    }

    public void addExercise(View v){
        NewExerciseDialog dialog = new NewExerciseDialog();
        dialog.show(getSupportFragmentManager(), "NewExerciseDialog");
    }

    @Override
    public void goHomeWithoutStack() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        finish();
        startActivity(i);
    }

    @Override
    public void toast(String message) {

    }

    @Override
    public void setDateVisible() {
        trainingDateTV.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDateInvisible() {
        trainingDateTV.setVisibility(View.GONE);
    }

    @Override
    public void setProgressBarCenterVisible() {
        progressBarCenter.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProgressBarCenterInvisible() {
        progressBarCenter.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setRetryButtonVisible() {
        retryButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setRetryButtonInvisible() {
        retryButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setFailedInformation(String message) {
        failInformation.setVisibility(View.VISIBLE);
        failInformation.setText(message);

    }

    @Override
    public void setFailedInformationInvisible() {
        failInformation.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setFloatingButtonInvisible() {
        floatingActionButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setFloatingButtonVisible() {
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProgressBarVisible() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProgressBarInvisible() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setButtonVisible() {
        saveTrainingButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setButtonInvisible() {
        saveTrainingButton.setVisibility(View.GONE);
    }

    @Override
    public void setButtonText(String text) {
        saveTrainingButton.setText(text);
    }



}
