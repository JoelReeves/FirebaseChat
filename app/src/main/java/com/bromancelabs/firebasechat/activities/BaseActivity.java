package com.bromancelabs.firebasechat.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.firebase.client.Firebase;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String FIREBASE_URL = "https://glowing-inferno-4886.firebaseio.com/";

    protected Firebase firebaseRef;
    protected Dialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResId());

        ButterKnife.bind(this);

        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase(FIREBASE_URL);
    }

    protected abstract int setLayoutResId();
}
