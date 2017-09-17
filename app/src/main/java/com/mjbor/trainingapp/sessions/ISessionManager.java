package com.mjbor.trainingapp.sessions;

import java.util.HashMap;

/**
 * Created by mjbor on 9/1/2017.
 */

public interface ISessionManager {
    public void createLoginSession(String token);
    public HashMap<String, String> getUserDetails();
    public void checkLogin();
    public void logoutUser();
    public boolean isLoggedIn();

}
