package com.mjbor.trainingapp.Training.plateCalculator;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mjbor on 9/29/2017.
 */

public class PlateCalculator {
    private double weight;
    private Double[] weights = new Double[] {20d, 10d, 5d, 2.5d, 1.25d};

    public PlateCalculator(double weight) {
        this.weight = weight;
    }

    public Map<Double, Integer> getPlates(){
        if(weight < 20)
            return null;

        double withoutBar = weight - 20d;
        double onOneSide = withoutBar/2d;
        int plates;

        Map<Double, Integer> map = new LinkedHashMap<>();
        for(Double w : weights){
            plates = (int)(onOneSide / w);
            onOneSide = onOneSide - plates * w;
            if(plates > 0)
                map.put(w, plates);
        }

        return map;
    }

    public String getResult(){
        if(weight <= 20)
            return "Lift empty bar";

        int plates;
        StringBuilder stringBuilder = new StringBuilder();
        double withoutBar = weight - 20d;
        double onOneSide = withoutBar/2d;

        DecimalFormat df = new DecimalFormat("###.##");

        for(Double w : weights){
            plates = (int)(onOneSide / w);
            onOneSide = onOneSide - plates * w;
            if(plates > 0){
                stringBuilder.append(plates + " x " + df.format(w) + "kg\n");
            }
        }

        return stringBuilder.toString();
    }


}
