package com.mjbor.trainingapp.dagger;

import com.mjbor.trainingapp.Login.view.LoginActivity;
import com.mjbor.trainingapp.Main.view.MainActivity;
import com.mjbor.trainingapp.Training.view.TrainingActivity;
import com.mjbor.trainingapp.sessions.SessionManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mjbor on 10/20/2017.
 */

@Singleton
@Component(modules = {AppModule.class, SessionModule.class})
public interface AppComponent {

    void inject(TrainingActivity target);
    void inject(MainActivity target);
    void inject(LoginActivity target);
    void inject(SessionManager target);
}

