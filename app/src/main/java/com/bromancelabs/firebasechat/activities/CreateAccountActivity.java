package com.bromancelabs.firebasechat.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import com.bromancelabs.firebasechat.BaseApplication;
import com.bromancelabs.firebasechat.R;
import com.bromancelabs.firebasechat.utils.SnackBarUtils;
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
    @Bind(R.id.txt_confirm_password) EditText confirmPasswordEditText;

    private String email;
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
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        confirmPassword = confirmPasswordEditText.getText().toString();

        if (isAccountCriteriaSatisfied()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();

            BaseApplication.getFirebase().createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> stringObjectMap) {
                    cancelProgressDialog();
                    Timber.d("Successfully created user account with uid: %s", stringObjectMap.get("uid"));
                    finish();
                    startActivity(LoginActivity.newIntent(CreateAccountActivity.this, email, password));
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    cancelProgressDialog();
                    Timber.e(firebaseError.getMessage());
                    SnackBarUtils.showSnackbar(emailEditText, R.string.create_account_error);
                }
            });
        }
    }

    private boolean isAccountCriteriaSatisfied() {
        int errorCount = 0;

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            errorCount++;
            SnackBarUtils.showSnackbar(emailEditText, R.string.create_account_missing_fields);
        }


        if (!password.equals(confirmPassword)) {
            errorCount++;
            SnackBarUtils.showSnackbar(emailEditText, R.string.create_account_password_mismatch);
        }

        Timber.d("Error count: %d", errorCount);

        return errorCount == 0;
    }

    private void cancelProgressDialog() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }
}
