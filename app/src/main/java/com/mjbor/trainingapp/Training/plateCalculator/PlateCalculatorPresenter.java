package com.mjbor.trainingapp.Training.plateCalculator;


import java.util.Map;

/**
 * Created by mjbor on 10/2/2017.
 */

public class PlateCalculatorPresenter {
    private double weight;
    private IPlateCalculatorView view;
    private PlateCalculator calculator;

    private int canvasWidth, canvasHeight;
    private int barWidth, barHeight;
    private int startPlateX, startPlateY;
    private int barX, barY;
    private boolean created;
    private int padding = 32;

    public PlateCalculatorPresenter(IPlateCalculatorView view, double weight) {
        this.weight = weight;
        this.view = view;

        view.setWeightLabel(weight);
        weightChanged(weight);
    }

    public void minusClicked(){
        this.weight -= 2.5;
        view.setWeightLabel(weight);
    }

    public void plusClicked(){
        this.weight += 2.5;
        view.setWeightLabel(weight);
    }

    public void weightChanged(double weight){
        this.weight = weight;
        calculator = new PlateCalculator(weight);

        view.setCreated(false);
        view.recreateSurfaceView();
        view.setInstructions(calculator.getResult());
    }


    public void draw(int canvasWidth, int canvasHeight){
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        drawBar();
        drawPlateSeparator();
        drawPlates();
    }

    public void drawBar(){
        barWidth = (int)(canvasWidth * 0.7);
        barHeight = (int)(canvasHeight * 0.06);
        barX = padding;
        barY = (canvasHeight/2) - (barHeight/2) ;

        view.setColor("#b4afaf");
        view.drawRectangle(barX, barY, barX+barWidth, barY+barHeight);
    }

    public void drawPlateSeparator(){
        int separatorWidth = (int)(canvasWidth * 0.1);
        int separatorHeight = barHeight * 3;
        int separatorX = padding + (barWidth / 5);
        int separatorY = (canvasHeight/2) - (separatorHeight/2) ;

        startPlateX = separatorX+separatorWidth;
        startPlateY = separatorY+separatorHeight;
        view.setColor("#FF616161");
        view.drawRectangle(separatorX, separatorY, separatorX+separatorWidth, separatorY+separatorHeight);
    }

    public void drawPlates(){
        int plateWidth = (int)(canvasWidth * 0.05);
        int plateHeight;

        Map<Double, Integer> plates = calculator.getPlates();
        if(plates == null){
            return;
        }

        for (Map.Entry<Double, Integer> entry : plates.entrySet()) {
            Double weight = entry.getKey();
            Integer platesCount = entry.getValue();

            plateHeight = (int)(canvasHeight * 0.1 * Math.sqrt(weight));
            startPlateY = (canvasHeight/2) - (plateHeight/2);

            if(platesCount > 5)
                platesCount = 5;

            for(int i = 0; i < platesCount; i++){
                view.setColor(getPlateColor(weight));
                view.drawRectangle(startPlateX, startPlateY, startPlateX+plateWidth, startPlateY+plateHeight);
                startPlateX += plateWidth;

            }
        }
    }



    public String getPlateColor(double weight){
        if(weight == 20d){
            return "#2E2C2F";
        }

        if(weight == 10d){
            return "#475B63";
        }

        if(weight == 5d){
            return "#729B79";
        }

        return "#BACDB0";

    }




}
