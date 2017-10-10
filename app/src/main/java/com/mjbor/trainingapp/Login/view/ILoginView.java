package com.mjbor.trainingapp.Login.view;

/**
 * Created by mjbor on 8/17/2017.
 */

public interface ILoginView {

    public void goToMain();
    public void toast(String message);
    public void promptRecordPopup();

    public void setProgressBarVisible();
    public void setProgressBarInvisible();
    public void setButtonText(String text);
}
