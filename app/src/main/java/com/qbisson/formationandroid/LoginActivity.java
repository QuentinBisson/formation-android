package com.qbisson.formationandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qbisson.formationandroid.services.ChatService;

import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Logging";

    private EditText login;
    private EditText password;
    private TextView loginError;
    private TextView passwordError;
    private Button submit;
    private Button clear;
    private FrameLayout progressBarHolder;
    private ChatService chatService;

    public LoginActivity() {
        chatService = new ChatService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        loginError = (TextView) findViewById(R.id.loginError);
        passwordError = (TextView) findViewById(R.id.passwordError);
        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlphaAnimation animation = new AlphaAnimation(0f, 1f);
                animation.setDuration(200);
                progressBarHolder.setAnimation(animation);
                progressBarHolder.setVisibility(View.VISIBLE);

                Call<Void> call = chatService.login(login.getText().toString(), password.getText().toString());
                call.enqueue(new retrofit2.Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        clearAnimation();
                        if (response.code() == 200) {
                            Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                            intent.putExtra("login", login.getText().toString());
                            intent.putExtra("password", password.getText().toString());

                            startActivity(intent);
                            finish();
                        } else if (response.code() == 401) {
                            // TODO login problem
                        } else {
                            // TODO Show potential error
                        }
                    }

                    private void clearAnimation() {
                        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
                        animation.setDuration(200);
                        progressBarHolder.setAnimation(animation);
                        progressBarHolder.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                        clearAnimation();
                        // TODO Show potential error
                    }
                });
            }
        });

        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login.setText("");
                password.setText("");
            }
        });

        login.addTextChangedListener(new ErrorTextWatcher(loginError));
        password.addTextChangedListener(new ErrorTextWatcher(passwordError));
        Log.i(TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    private class ErrorTextWatcher implements TextWatcher {
        private TextView view;

        public ErrorTextWatcher(TextView view) {
            this.view = view;
        }

        public void afterTextChanged(Editable s) {
            // Nothing here
        }

        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
            // Nothing here
        }

        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
            if (s == null || s.length() == 0) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }

            submit.setVisibility(passwordError.getVisibility() == View.GONE && loginError.getVisibility() == View.GONE
                    ? View.VISIBLE
                    : View.GONE);
        }
    }
}
