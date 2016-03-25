package com.bromancelabs.firebasechat.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import com.bromancelabs.firebasechat.BaseApplication;
import com.bromancelabs.firebasechat.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class CreateAccountActivity extends AppCompatActivity {

    @Bind(R.id.txt_login_username) EditText usernameEditText;
    @Bind(R.id.txt_login_password) EditText passwordEditText;
    @Bind(R.id.txt_confirm_password) EditText confirmPasswordEditText;

    private String username;
    private String password;
    private String confirmPassword;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_sign_up)
    public void signUpClick() {
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        confirmPassword = confirmPasswordEditText.getText().toString();

        if (isAccountCriteriaSatisfied()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();

            BaseApplication.getFirebase().createUser(username, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> stringObjectMap) {
                    cancelProgressDialog();
                    Timber.d("Successfully created user account with uid: %s", stringObjectMap.get("uid"));
                    showSnackbar(R.string.create_account_successful);
                    finish();
                    startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    cancelProgressDialog();
                    Timber.e(firebaseError.getMessage());
                    showSnackbar(R.string.create_account_error);
                }
            });
        }
    }

    private boolean isAccountCriteriaSatisfied() {
        int errorCount = 0;

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            errorCount++;
            showSnackbar(R.string.create_account_missing_fields);
        }


        if (!password.equals(confirmPassword)) {
            errorCount++;
            showSnackbar(R.string.create_account_password_mismatch);
        }

        Timber.d("Error count: %d", errorCount);

        return errorCount == 0;
    }

    private void cancelProgressDialog() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    private void showSnackbar(int message) {
        Snackbar.make(usernameEditText, message, Snackbar.LENGTH_SHORT).show();
    }
}
