package com.mjbor.trainingapp;

import com.mjbor.trainingapp.Home.IHomeFragment;
import com.mjbor.trainingapp.Register.view.IRegisterView;
import com.mjbor.trainingapp.Training.view.ITrainingView;
import com.mjbor.trainingapp.models.Exercise;
import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.sessions.ISessionManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mjbor on 9/22/2017.
 */

public class MockFactory {

    public static IHomeFragment mockHomeFragment(){
        IHomeFragment iHomeFragment = new IHomeFragment() {
            @Override
            public void setEx1(String text) {

            }

            @Override
            public void refreshData() {

            }
        };

        return iHomeFragment;
    }

    public static IRegisterView mockRegisterView(){
        IRegisterView registerView = new IRegisterView() {
            @Override
            public void onRegisterSuccess() {

            }

            @Override
            public void toast(String message) {

            }

            @Override
            public void setProgressBarVisible() {

            }

            @Override
            public void setProgressBarInvisible() {

            }

            @Override
            public void setButtonText(String text) {

            }

            @Override
            public void promptRecordPopup() {

            }
        };

        return registerView;
    }

    public static ITrainingView mockTrainingFragment(){
        ITrainingView iHomeFragment = new ITrainingView() {
            @Override
            public void goHomeWithoutStack() {

            }

            @Override
            public void toast(String message) {

            }

            @Override
            public void setProgressBarVisible() {

            }

            @Override
            public void setProgressBarInvisible() {

            }

            @Override
            public void setButtonText(String text) {

            }


            @Override
            public void showTraining(Training training) {

            }

            @Override
            public void promptPlateDialog(double weight) {

            }

            @Override
            public void setButtonVisible() {

            }

            @Override
            public void setButtonInvisible() {

            }

            @Override
            public void setProgressBarCenterVisible() {

            }

            @Override
            public void setProgressBarCenterInvisible() {

            }

            @Override
            public void setRetryButtonVisible() {

            }

            @Override
            public void setRetryButtonInvisible() {

            }

            @Override
            public void setFailedInformation(String message) {

            }

            @Override
            public void setFailedInformationInvisible() {

            }

            @Override
            public void setFloatingButtonInvisible() {

            }

            @Override
            public void setFloatingButtonVisible() {

            }
        };

        return iHomeFragment;
    }

    public static ISessionManager mockISessionManager(){
        ISessionManager sessionManager = new ISessionManager() {
            @Override
            public void createLoginSession(String token) {

            }

            @Override
            public HashMap<String, String> getUserDetails() {
                return null;
            }

            @Override
            public void checkLogin() {

            }

            @Override
            public String getUserToken() {
                return mockToken();
            }

            @Override
            public void logoutUser() {

            }

            @Override
            public boolean isLoggedIn() {
                return true;
            }
        };

        return sessionManager;
    }

    public static String mockToken(){
        return "3a27dc39a7e2a4c1be582bea7817abb9";
    }
}
