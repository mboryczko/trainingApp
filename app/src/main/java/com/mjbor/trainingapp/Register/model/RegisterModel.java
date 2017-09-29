package com.mjbor.trainingapp.Register.model;

import com.mjbor.trainingapp.models.Training;
import com.mjbor.trainingapp.models.User;

/**
 * Created by mjbor on 9/23/2017.
 */

public class RegisterModel {

    private Training trainingA;
    private Training trainingB;
    private User user;

    public RegisterModel(Training trainingA, Training trainingB, User user) {
        this.trainingA = trainingA;
        this.trainingB = trainingB;
        this.user = user;
    }

    public RegisterModel() {
    }

    public Training getTrainingA() {
        return trainingA;
    }

    public void setTrainingA(Training trainingA) {
        this.trainingA = trainingA;
    }

    public Training getTrainingB() {
        return trainingB;
    }

    public void setTrainingB(Training trainingB) {
        this.trainingB = trainingB;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
