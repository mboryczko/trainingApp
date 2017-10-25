package com.mjbor.trainingapp.Utils;

import android.graphics.Color;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mjbor on 10/24/2017.
 */

public class ColorUtils {


    public static Integer getColor(int i){
        List<Integer> list = Arrays.asList(Color.RED,Color.YELLOW, Color.MAGENTA, Color.BLUE, Color.GREEN, Color.BLACK);

        return list.get(i);
    }
}
