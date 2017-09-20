package com.mjbor.trainingapp.models;

/**
 * Created by mjbor on 9/19/2017.
 */

public class Exercise
{
    private String[] reps;

    private String weight;

    private String name;

    public Exercise(String[] reps, String weight, String name) {
        this.reps = reps;
        this.weight = weight;
        this.name = name;
    }

    public String[] getReps ()
    {
        return reps;
    }

    public void setReps (String[] reps)
    {
        this.reps = reps;
    }

    public String getWeight ()
    {
        return weight;
    }

    public void setWeight (String weight)
    {
        this.weight = weight;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [reps = "+reps+", weight = "+weight+", name = "+name+"]";
    }
}