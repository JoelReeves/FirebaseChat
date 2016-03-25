package com.bromancelabs.firebasechat.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import com.bromancelabs.firebasechat.BaseApplication;
import com.bromancelabs.firebasechat.R;
import com.bromancelabs.firebasechat.utils.SnackBarUtils;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;


public class LoginActivity extends AppCompatActivity {

    private static final String EXTRA_EMAIL = "extra_email";
    private static final String EXTRA_PASSWORD = "extra_password";

    @Bind(R.id.txt_login_email) EditText emailEditText;
    @Bind(R.id.txt_login_password) EditText passwordEditText;

    private ProgressDialog progressDialog;

    public static Intent newIntent(Context context, String email, String password) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_EMAIL, email);
        intent.putExtra(EXTRA_PASSWORD, password);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String accountEmail = getIntent().getStringExtra(EXTRA_EMAIL);
            String accountPassword = getIntent().getStringExtra(EXTRA_PASSWORD);
            login(accountEmail, accountPassword);
        }
    }

    @OnClick(R.id.btn_reset_password)
    public void resetPasswordButtonClick() {
        startActivity(new Intent(this, ResetPasswordActivity.class));
    }

    @OnClick(R.id.btn_login)
    public void loginButtonClick() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
            login(email, password);
        } else {
            SnackBarUtils.showSnackbar(emailEditText, R.string.create_account_missing_fields);
        }
    }

    private void login(String email, String password) {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        BaseApplication.getFirebase().authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                cancelProgressDialog();
                Timber.d("User string: %s", authData.toString());
                finish();
                startActivity(new Intent(LoginActivity.this, ChatActivity.class));
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                cancelProgressDialog();
                Timber.e(firebaseError.getMessage());
                SnackBarUtils.showSnackbar(emailEditText, firebaseError.getMessage());
            }
        });
    }

    private void cancelProgressDialog() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }
}
