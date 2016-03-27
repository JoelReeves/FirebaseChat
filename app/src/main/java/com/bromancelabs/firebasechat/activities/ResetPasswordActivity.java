package com.bromancelabs.firebasechat.activities;

import android.text.TextUtils;
import android.widget.EditText;

import com.bromancelabs.firebasechat.BaseApplication;
import com.bromancelabs.firebasechat.R;
import com.bromancelabs.firebasechat.utils.SnackBarUtils;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import butterknife.Bind;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity {

    @Bind(R.id.txt_email_reset) EditText emailEditText;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_reset_password;
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
