package com.mjbor.trainingapp.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mjbor on 9/19/2017.
 */

public class Exercise implements Serializable
{
    private List<Integer> reps;

    private double weight;

    private String name;

    private int reps_to_do;

    public int getReps_to_do() {
        return reps_to_do;
    }

    public void setReps_to_do(int reps_to_do) {
        this.reps_to_do = reps_to_do;
    }

    public Exercise(List<Integer> reps, double weight, String name, int reps_to_do) {
        this.reps = reps;
        this.weight = weight;
        this.name = name;
        this.reps_to_do = reps_to_do;
    }

    public List<Integer> getReps() {
        return reps;
    }

    public void setReps(List<Integer> reps) {
        this.reps = reps;
    }

    public void setReps(int position, int value){
        this.reps.set(position, value);
    }

    public double getWeight ()
    {
        return weight;
    }

    public void setWeight (double weight)
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