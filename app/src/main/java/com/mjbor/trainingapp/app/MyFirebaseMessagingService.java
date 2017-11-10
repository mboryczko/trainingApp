package com.mjbor.trainingapp.app;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by mjbor on 11/7/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    // Sets an ID for the notification, so it can be updated
    private static final String TAG = "FCM Service";

    public MyFirebaseMessagingService() {
        super();
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
    }
}