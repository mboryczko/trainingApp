package com.mjbor.trainingapp.Training.view;

import com.mjbor.trainingapp.Register.dialog.TrainingHistoryDialog;
import com.mjbor.trainingapp.models.Exercise;
import com.mjbor.trainingapp.models.Training;

import java.util.List;

/**
 * Created by mjbor on 9/20/2017.
 */

public interface ITrainingView {

    public void goHomeWithoutStack();
    public void toast(String message);

    public void setProgressBarVisible();
    public void setProgressBarInvisible();
    public void setButtonText(String text);

    public void showTraining(Training training);
    public void promptPlateDialog(double weight);


}
