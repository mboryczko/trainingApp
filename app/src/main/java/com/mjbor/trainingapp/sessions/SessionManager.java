package com.mjbor.trainingapp.sessions;

/**
 * Created by mjbor on 8/28/2017.
 */


import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.mjbor.trainingapp.Login.view.LoginActivity;
import com.mjbor.trainingapp.Utils.Constants;
import com.mjbor.trainingapp.app.TrainingApplication;

import javax.inject.Inject;

import static com.mjbor.trainingapp.Utils.Constants.TOKEN;


public class SessionManager implements ISessionManager {
    // Shared Preferences

    @Inject SharedPreferences pref;
    @Inject Editor editor;

    Context _context;

/*    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "com.mjbor";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String TOKEN = "token";*/



    public SessionManager(Context context) {
        this._context = context;
        ((TrainingApplication)context).getAppComponent().inject(this);

        /*pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();*/
    }


    public String getUserToken(){
        return pref.getString(TOKEN, null);
    }


    /**
     * Create login session
     * */
    public void createLoginSession(String token){
        // Storing login value as TRUE
        editor.putBoolean(Constants.IS_LOGIN, true);
        editor.putString(TOKEN, token);

        // commit changes
        editor.commit();
    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        /*
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        */

        // return user
        return user;
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();


    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(Constants.IS_LOGIN, false);
    }

}
