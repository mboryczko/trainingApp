package com.mjbor.trainingapp.dagger;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;

import com.mjbor.trainingapp.Utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mjbor on 10/21/2017.
 */

@Module
public class SessionModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPrefs(Context context) {
        return context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
    }

    @Provides
    SharedPreferences.Editor provideEditor(SharedPreferences pref){
        return pref.edit();
    }

}
