package com.bromancelabs.firebasechat.activities;

import android.content.Intent;

import com.bromancelabs.firebasechat.R;

import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_home;
    }

    @OnClick(R.id.btn_home_login)
    public void loginButtonClick() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.btn_home_create_account)
    public void createAccountClick() {
        startActivity(new Intent(this, CreateAccountActivity.class));
    }
}
