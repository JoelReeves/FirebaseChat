package com.bromancelabs.firebasechat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bromancelabs.firebasechat.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void loginButtonClick() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.btn_create_account)
    public void createAccountClick() {
        startActivity(new Intent(this, CreateAccountActivity.class));
    }
}
