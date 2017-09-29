package com.mjbor.trainingapp.models;

/**
 * Created by mjbor on 9/19/2017.
 */

public class Training
{
    private boolean error;
    private String message;
    private String token;
    private int type;

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

    private Exercise[] exercises;

    public Exercise[] getExercises ()
    {
        return exercises;
    }

    public void setExercises (Exercise[] exercises)
    {
        this.exercises = exercises;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [exercises = "+exercises+"]";
    }
}
