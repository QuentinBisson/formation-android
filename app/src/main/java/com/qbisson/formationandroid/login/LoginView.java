package com.qbisson.formationandroid.login;

public interface LoginView {

    void displayLoading(boolean display);

    void onLoginValidation(boolean valid);

    void onPasswordValidation(boolean valid);

    void goToMessages(String login, String password);

    void onUnauthorized();

    void onError();
}
