package com.mjbor.trainingapp.Training.plateCalculator;

/**
 * Created by mjbor on 9/29/2017.
 */

public class PlateCalculator {
    private double weight;
    private double rest;

    private int twenty, ten, five, twoAndHalf, oneTwentyFive;


    public PlateCalculator(double weight) {
        this.weight = weight;
    }

    public String getResult(){
        if(weight < 20){
            return "Lift empty bar";
        }

        double withoutBar = weight - 20;
        double onOneSide = withoutBar/2;




        twenty = (int)(onOneSide / 20);
        onOneSide  = onOneSide - twenty * 20;
        if(onOneSide >= 10){
            onOneSide -= 10;
            ten = 1;
        }

        if(onOneSide >= 5){
            onOneSide -= 5;
            five = 1;
        }

        if(onOneSide >= 2.5){
            onOneSide -= 2.5;
            twoAndHalf = 1;
        }

        if(onOneSide >= 1.25){
            onOneSide -= 1.25;
            oneTwentyFive = 1;
        }


        String result = "";
        if(twenty > 0){
            result += twenty + " x 20kg\n";
        }

        if(ten > 0){
            result += ten + " x 10kg\n";
        }

        if(five > 0){
            result += five + " x 5kg\n";
        }

        if(twoAndHalf > 0){
            result += twoAndHalf + " x 2.5kg\n";
        }

        if(oneTwentyFive > 0){
            result += oneTwentyFive + " x 1.25kg\n";
        }

        return result;
    }

}
