package com.qbisson.formationandroid.chat;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;

public class SendMessageCallback implements retrofit2.Callback<Void> {

    private final ChatPresenter presenter;
    private final String content;

    public SendMessageCallback(ChatPresenter presenter, String content) {
        this.presenter = presenter;
        this.content = content;
    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
            presenter.onSentMessage();
        } else if (response.code() == 400) {
            // We retry to add the message till the UUID is available
            presenter.sendMessage(content);
        } else if (response.code() == 401) {
            presenter.onUnauthorized();
        } else {
            presenter.onError();
        }
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        Log.d("Error", t.getMessage());
        presenter.onError();
    }
}