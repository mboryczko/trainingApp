package com.mjbor.trainingapp.models;

/**
 * Created by mjbor on 9/19/2017.
 */

public class Exercise
{
    private int[] reps;

    private double weight;

    private String name;

    private int reps_to_do;

    public int getReps_to_do() {
        return reps_to_do;
    }

    public void setReps_to_do(int reps_to_do) {
        this.reps_to_do = reps_to_do;
    }

    public Exercise(int[] reps, double weight, String name, int reps_to_do) {
        this.reps = reps;
        this.weight = weight;
        this.name = name;
        this.reps_to_do = reps_to_do;
    }

    public void setReps(int position, int reps){
        this.reps[position] = reps;
    }

    public int[] getReps ()
    {
        return reps;
    }

    public void setReps (int[] reps)
    {
        this.reps = reps;
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