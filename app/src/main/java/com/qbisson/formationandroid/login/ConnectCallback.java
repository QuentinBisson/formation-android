package com.qbisson.formationandroid.login;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;

class ConnectCallback implements retrofit2.Callback<Void> {
    private final String login;
    private final String password;
    private LoginPresenter presenter;

    public ConnectCallback(LoginPresenter presenter, String login, String password) {
        this.login = login;
        this.password = password;
        this.presenter = presenter;
    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
            presenter.onLogin(login, password);
        } else if (response.code() == 401) {
            presenter.onUnauthorized();
        } else {
            presenter.onLoginError();
        }
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        Log.d("Error", t.getMessage());
        presenter.onLoginError();
    }
}