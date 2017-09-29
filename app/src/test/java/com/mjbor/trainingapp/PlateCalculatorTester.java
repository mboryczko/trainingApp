package com.mjbor.trainingapp;

import com.mjbor.trainingapp.Training.plateCalculator.PlateCalculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * Created by mjbor on 9/29/2017.
 */

public class PlateCalculatorTester {

    @Test
    public void test(){
        PlateCalculator calculator;
        String result;


        calculator = new PlateCalculator(107.5);
        result = calculator.getResult();
        assertEquals(result, "2 x 20kg\n" +
                "1 x 2.5kg\n" +
                "1 x 1.25kg\n");




        calculator = new PlateCalculator(245);
        result = calculator.getResult();
        assertEquals(result, "5 x 20kg\n" +
                "1 x 10kg\n" +
                "1 x 2.5kg\n");

        calculator = new PlateCalculator(42.5);
        result = calculator.getResult();
        assertEquals(result, "1 x 10kg\n" +
                "1 x 1.25kg\n");


        calculator = new PlateCalculator(22.5);
        result = calculator.getResult();
        assertEquals(result, "1 x 1.25kg\n");

        calculator = new PlateCalculator(17.5);
        result = calculator.getResult();
        assertEquals(result, "Lift empty bar");



    }
}
