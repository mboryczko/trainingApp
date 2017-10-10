package com.mjbor.trainingapp.Progress;

import com.mjbor.trainingapp.models.ChartResponse;

/**
 * Created by mjbor on 10/3/2017.
 */

public interface IProgressFragment {

    public void showAllCharts(ChartResponse chartResponse);

    public void showChart(ChartResponse chartResponse);
}
