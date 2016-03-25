package com.bromancelabs.firebasechat;

import android.app.Application;

import com.firebase.client.Firebase;

import timber.log.Timber;

public class BaseApplication extends Application {

    private static final String FIREBASE_URL = "https://glowing-inferno-4886.firebaseio.com/";
    private static Firebase instance = null;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }

    public static Firebase getFirebase() {
        if (instance == null) {
            instance = new Firebase(FIREBASE_URL);
        }
        return instance;
    }
}
