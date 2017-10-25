package com.mjbor.trainingapp.Utils;

import com.mjbor.trainingapp.models.Exercise;

import java.util.List;

/**
 * Created by mjbor on 10/22/2017.
 */

public class StringUtils {

    public static String formatNumber(int number){
        if(number < 10){
            return "0" + number;
        }
        return Integer.toString(number);
    }


    public static String prepareListOfBestResults(List<Exercise> exercises){
        String list = "";
        if(exercises != null){
            for(Exercise e : exercises){
                list += "• " + e.getName() + " - " +  e.getWeight() + "\n";
            }
        }

        else
            list = "No records set yet";


        return list;
    }

}
