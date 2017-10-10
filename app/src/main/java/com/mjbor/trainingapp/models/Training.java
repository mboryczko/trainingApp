package com.mjbor.trainingapp.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mjbor on 9/19/2017.
 */

public class Training implements Serializable
{
    private String training_date;
    private boolean error;
    private int id_training;
    private String message;
    private String token;
    private int type;


    public String getTraining_date() {
        return training_date;
    }

    public void setTraining_date(String training_date) {
        this.training_date = training_date;
    }

    public int getId_training() {
        return id_training;
    }

    public void setId_training(int id_training) {
        this.id_training = id_training;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private List<Exercise> exercises;

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [exercises = "+exercises+"]";
    }
}
