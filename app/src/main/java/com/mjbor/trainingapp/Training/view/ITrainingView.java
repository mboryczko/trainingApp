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
    public void setButtonVisible();
    public void setButtonInvisible();
    public void setButtonText(String text);
    public void setProgressBarCenterVisible();
    public void setProgressBarCenterInvisible();
    public void setRetryButtonVisible();
    public void setRetryButtonInvisible();
    public void setFailedInformation(String message);
    public void setFailedInformationInvisible();
    public void setFloatingButtonInvisible();
    public void setFloatingButtonVisible();
    public void setTimeAndDateVisible();
    public void setTimeAndDateInvisible();
    public void setInfoVisible();
    public void setInfoInvisible();
    public void setTimeAndDateText(String text);
    public void setInfoText(String text);
    public void startTimer();
    public void resetTimer();

    public void showTraining(Training training);
    public void promptPlateDialog(double weight);


}
