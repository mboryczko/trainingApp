package com.mjbor.trainingapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mjbor on 9/17/2017.
 */

public class UserUtils {
    private static String PREF_USER_TOKEN = "pref-user-language";

    public static void setUserToken(Context context, String token){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(PREF_USER_TOKEN, token).apply();
    }

    public static String getUserToken(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PREF_USER_TOKEN, "");
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return !preferences.getString(PREF_USER_TOKEN, "").matches("");
    }
}
