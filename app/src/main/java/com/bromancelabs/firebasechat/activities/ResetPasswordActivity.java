package com.bromancelabs.firebasechat.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import com.bromancelabs.firebasechat.BaseApplication;
import com.bromancelabs.firebasechat.R;
import com.bromancelabs.firebasechat.utils.SnackBarUtils;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends AppCompatActivity {

    @Bind(R.id.txt_email_reset) EditText emailEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_reset_confirm)
    public void confirmResetClick() {
        final String email = emailEditText.getText().toString();

        if (!TextUtils.isEmpty(email)) {
            BaseApplication.getFirebase().resetPassword(email, new Firebase.ResultHandler() {
                @Override
                public void onSuccess() {
                    emailEditText.setText("");
                    SnackBarUtils.showSnackbar(emailEditText, R.string.reset_email_success);
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    SnackBarUtils.showSnackbar(emailEditText, firebaseError.getMessage());
                }
            });
        } else {
            SnackBarUtils.showSnackbar(emailEditText, R.string.reset_password_missing_email);
        }
    }
}
