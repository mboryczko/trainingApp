package com.mjbor.trainingapp.models;

/**
 * Created by mjbor on 9/19/2017.
 */

public class Training
{
    private boolean error;
    private String message;

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

    private Exercises[] exercises;

    public Exercises[] getExercises ()
    {
        return exercises;
    }

    public void setExercises (Exercises[] exercises)
    {
        this.exercises = exercises;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [exercises = "+exercises+"]";
    }
}
