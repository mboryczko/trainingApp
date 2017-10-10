package com.mjbor.trainingapp.models;

import java.util.List;

/**
 * Created by mjbor on 10/3/2017.
 */

public class ChartResponse
{
    private String message;

    private List<ChartPoint> squat;

    private boolean error;

    private List<ChartPoint> bench;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ChartPoint> getSquat() {
        return squat;
    }

    public void setSquat(List<ChartPoint> squat) {
        this.squat = squat;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ChartPoint> getBench() {
        return bench;
    }

    public void setBench(List<ChartPoint> bench) {
        this.bench = bench;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", squat = "+squat+", error = "+error+", bench = "+bench+"]";
    }
}

