package com.qbisson.formationandroid.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qbisson.formationandroid.R;
import com.qbisson.formationandroid.SettingUtils;
import com.qbisson.formationandroid.chat.ChatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.login)
    EditText login;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.loginError)
    TextView loginError;
    @BindView(R.id.passwordError)
    TextView passwordError;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.progressBarHolder)
    View loadingWheel;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AlphaAnimation loadingAnimation = new AlphaAnimation(0f, 1f);
        loadingAnimation.setDuration(200);
        loadingWheel.setAnimation(loadingAnimation);

        presenter = new LoginPresenter(this);
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();

        super.onDestroy();
    }

    @OnTextChanged(R.id.login)
    public void onLoginChanged(Editable editable) {
        presenter.validateLogin(editable.toString());
    }

    @OnTextChanged(R.id.password)
    public void onPasswordChanged(Editable editable) {
        presenter.validatePassword(editable.toString());
    }

    @OnClick(R.id.clear)
    public void onClear(View view) {
        login.setText("");
        password.setText("");
    }

    @OnClick(R.id.submit)
    public void onSubmit(View view) {
        presenter.connect(login.getText().toString(), password.getText().toString());
    }

    @Override
    public void displayLoading(boolean display) {
        loadingWheel.setVisibility(display ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoginValidation(boolean valid) {
        loginError.setVisibility(valid ? View.GONE : View.VISIBLE);
        toggleButtonVisibility();
    }

    @Override
    public void onPasswordValidation(boolean valid) {
        passwordError.setVisibility(valid ? View.GONE : View.VISIBLE);
        toggleButtonVisibility();
    }

    @Override
    public void goToMessages(String login, String password) {
        SharedPreferences settings = getSharedPreferences(SettingUtils.USER_PREFERENCES, MODE_PRIVATE);
        settings.edit()
                .putString(SettingUtils.LOGIN_PREFERENCE, login)
                .putString(SettingUtils.PASSWORD_PREFERENCE, password)
                .apply();

        startActivity(new Intent(this, ChatActivity.class));
        finish();
    }

    private void toggleButtonVisibility() {
        submit.setVisibility(
                passwordError.getVisibility() == View.GONE && loginError.getVisibility() == View.GONE
                        ? View.VISIBLE
                        : View.GONE);
    }

    @Override
    public void onUnauthorized() {
        Toast.makeText(this, "Unauthorized access !", Toast.LENGTH_SHORT);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Could not connect to server !", Toast.LENGTH_SHORT);
    }
}
