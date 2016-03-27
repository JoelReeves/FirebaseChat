package com.bromancelabs.firebasechat.activities;

import android.app.Dialog;
import android.text.TextUtils;
import android.widget.EditText;

import com.bromancelabs.firebasechat.R;
import com.bromancelabs.firebasechat.utils.DialogUtils;
import com.bromancelabs.firebasechat.utils.SnackBarUtils;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

public class CreateAccountActivity extends BaseActivity {

    @Bind(R.id.txt_login_email) EditText emailEditText;
    @Bind(R.id.txt_login_password) EditText passwordEditText;
    @Bind(R.id.txt_confirm_password) EditText confirmPasswordEditText;

    private String email;
    private String password;
    private String confirmPassword;

    private Dialog progressDialog;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_create_account;
    }

    @OnClick(R.id.btn_sign_up)
    public void signUpClick() {
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        confirmPassword = confirmPasswordEditText.getText().toString();

        if (isAccountCriteriaSatisfied()) {
            progressDialog = DialogUtils.showProgressDialog(this);

            firebaseRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> stringObjectMap) {
                    DialogUtils.cancelProgressDialog(progressDialog);
                    Timber.d("Successfully created user account with uid: %s", stringObjectMap.get("uid"));
                    finish();
                    startActivity(LoginActivity.newIntent(CreateAccountActivity.this, email, password));
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    DialogUtils.cancelProgressDialog(progressDialog);
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
}
