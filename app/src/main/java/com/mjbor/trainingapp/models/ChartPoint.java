package com.mjbor.trainingapp.models;

import java.util.Date;

/**
 * Created by mjbor on 10/3/2017.
 */

public class ChartPoint {

    private float weight;
    private Date training_date;

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Date getTraining_date() {
        return training_date;
    }

    public void setTraining_date(Date training_date) {
        this.training_date = training_date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [weight = "+weight+", training_date = "+training_date+"]";
    }
}
