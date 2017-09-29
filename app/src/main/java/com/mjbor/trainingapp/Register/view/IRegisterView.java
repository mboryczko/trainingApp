package com.mjbor.trainingapp.Register.view;

/**
 * Created by mjbor on 8/17/2017.
 */

public interface IRegisterView {

    public void onRegisterSuccess();
    public void toast(String message);

    public void setProgressBarVisible();
    public void setProgressBarInvisible();
    public void setButtonText(String text);

    public void promptRecordPopup();

}
