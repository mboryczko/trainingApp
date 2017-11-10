package com.mjbor.trainingapp.models;

import java.util.List;

/**
 * Created by mjbor on 10/23/2017.
 */

public class AllChartResponse {

    List<ChartResponse> exercises;
    private boolean error;
    private String message;

    public List<ChartResponse> getExercises() {
        return exercises;
    }

    public void setExercises(List<ChartResponse> exercises) {
        this.exercises = exercises;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ChartResponse> getList() {
        return exercises;
    }

    public void setList(List<ChartResponse> list) {
        this.exercises = list;
    }

}
