package com.qbisson.formationandroid.login;

import android.util.Log;

import com.qbisson.formationandroid.chat.service.ChatService;

import retrofit2.Call;

public class LoginPresenter {
    private static final String TAG = "Logging";

    private LoginView view;
    private ChatService chatService;

    public LoginPresenter(LoginView view) {
        this.view = view;
        this.chatService = new ChatService();
    }

    public void onCreate() {
        Log.i(TAG, "onCreate");
        this.view.displayLoading(false);
    }

    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        view = null;
        chatService = null;
    }

    public void validateLogin(String login) {
        view.onLoginValidation(!login.isEmpty());
    }

    public void validatePassword(String password) {
        view.onPasswordValidation(password.length() > 3);
    }

    public void connect(final String login, final String password) {
        Call<Void> call = chatService.login(login, password);
        call.enqueue(new ConnectCallback(this, login, password));
    }

    public void onLogin(String login, String password) {
        view.displayLoading(false);
        view.goToMessages(login, password);
    }

    public void onUnauthorized() {
        view.displayLoading(false);
        view.onUnauthorized();
    }

    public void onLoginError() {
        view.displayLoading(false);
        view.onError();
    }
}
