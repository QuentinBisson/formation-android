package com.qbisson.formationandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qbisson.formationandroid.chat.ChatActivity;
import com.qbisson.formationandroid.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigate();
    }

    private void navigate() {
        SharedPreferences preferences = getSharedPreferences(SettingUtils.USER_PREFERENCES, MODE_PRIVATE);
        String login = preferences.getString(SettingUtils.LOGIN_PREFERENCE, "");
        String password = preferences.getString(SettingUtils.PASSWORD_PREFERENCE, "");

        Intent intent;
        if (login.isEmpty() || password.isEmpty()) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, ChatActivity.class);
        }

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        navigate();
    }
}
