package com.mjbor.trainingapp.models;

/**
 * Created by mjbor on 10/22/2017.
 */

public class SetEvent {

    private String message;
    private boolean failed;

    public SetEvent(String message, boolean failed) {
        this.message = message;
        this.failed = failed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }
}
