package com.mjbor.trainingapp.Training.model;

import android.graphics.Color;

/**
 * Created by mjbor on 9/20/2017.
 */

public class Circle {
    private int color;
    private int value;
    private int startValue;

    public int getStartValue() {
        return startValue;
    }

    public void setStartValue(int startValue) {
        this.startValue = startValue;
    }

    public Circle(int color, int value) {
        this.color = color;
        this.value = value;
    }

    public Circle(int color, int value, int startValue) {
        this.color = color;
        this.value = value;
        this.startValue = startValue;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
