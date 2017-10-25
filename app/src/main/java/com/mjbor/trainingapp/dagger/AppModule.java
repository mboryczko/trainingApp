package com.mjbor.trainingapp.dagger;

import android.app.Application;
import android.content.Context;

import com.mjbor.trainingapp.sessions.ISessionManager;
import com.mjbor.trainingapp.sessions.SessionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mjbor on 10/21/2017.
 */

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public ISessionManager provideSessionManager(Context context) {
        return new SessionManager(context);
    }


}