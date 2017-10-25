package com.mjbor.trainingapp.app;

import android.app.Application;

import com.mjbor.trainingapp.dagger.AppComponent;
import com.mjbor.trainingapp.dagger.AppModule;
import com.mjbor.trainingapp.dagger.DaggerAppComponent;

/**
 * Created by mjbor on 10/20/2017.
 */

public class TrainingApplication extends Application {

    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent initDagger(TrainingApplication application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger(this);
    }

}
