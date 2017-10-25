package com.mjbor.trainingapp.Utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by mjbor on 10/24/2017.
 */

public class DateUtils {

    public static int getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
