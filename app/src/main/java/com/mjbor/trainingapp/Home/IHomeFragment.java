package com.mjbor.trainingapp.Home;

import com.mjbor.trainingapp.models.Training;

/**
 * Created by mjbor on 9/19/2017.
 */

public interface IHomeFragment {

    public void setEx1(String text);
    public void trainingFetched(Training trainingFetched);

    public void refreshData();

}
