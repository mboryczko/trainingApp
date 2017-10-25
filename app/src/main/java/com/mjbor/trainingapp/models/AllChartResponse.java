package com.mjbor.trainingapp.models;

import java.util.List;

/**
 * Created by mjbor on 10/23/2017.
 */

public class AllChartResponse {

    List<ChartResponse> exercises;

    public List<ChartResponse> getList() {
        return exercises;
    }

    public void setList(List<ChartResponse> list) {
        this.exercises = list;
    }
}
