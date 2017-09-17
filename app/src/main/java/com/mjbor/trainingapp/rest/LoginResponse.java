package com.mjbor.trainingapp.rest;

/**
 * Created by mjbor on 9/1/2017.
 */

public class LoginResponse {
    private boolean error;
    private String message;
    private String token;

    public LoginResponse(boolean error, String message, String token) {
        this.error = error;
        this.message = message;
        this.token = token;
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
