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

    @Bind(R.id.txt_login_email) EditText emailEditText;
    @Bind(R.id.txt_login_password) EditText passwordEditText;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_sign_up)
    public void signUpClick() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();

            BaseApplication.getFirebase().createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> stringObjectMap) {
                    cancelProgressDialog();
                    Timber.d("Successfully created user account with uid: %s", stringObjectMap.get("uid"));
                    Snackbar.make(emailEditText, "Account successfully created", Snackbar.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    cancelProgressDialog();
                    Timber.e(firebaseError.getMessage());
                    Snackbar.make(emailEditText, "Error creating account", Snackbar.LENGTH_LONG).show();
                }
            });
        } else {
            Snackbar.make(emailEditText, "Please fill out both fields", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void cancelProgressDialog() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }
}
