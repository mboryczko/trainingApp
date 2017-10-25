package com.mjbor.trainingapp.models;

import java.util.List;

/**
 * Created by mjbor on 10/3/2017.
 */

public class ChartResponse
{
    private String message;
    private String exerciseName;
    private List<ChartPoint> exercise;

    private boolean error;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }


    public List<ChartPoint> getExercise() {
        return exercise;
    }

    public void setExercise(List<ChartPoint> exercise) {
        this.exercise = exercise;
    }
}

