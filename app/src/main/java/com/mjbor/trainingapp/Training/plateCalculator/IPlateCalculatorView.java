package com.mjbor.trainingapp.Training.plateCalculator;

/**
 * Created by mjbor on 10/2/2017.
 */

public interface IPlateCalculatorView {

    public void setWeightLabel(double weight);
    public void setInstructions(String instructions);
    public void recreateSurfaceView();
    public void setCreated(boolean b);

    public void drawRectangle(int left, int top, int right, int bottom);
    public void setColor(String hex);
}
