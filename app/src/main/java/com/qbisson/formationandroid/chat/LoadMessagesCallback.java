package com.qbisson.formationandroid.chat;

import android.util.Log;

import com.qbisson.formationandroid.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LoadMessagesCallback implements retrofit2.Callback<List<Message>> {
    private final ChatPresenter presenter;

    public LoadMessagesCallback(ChatPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
        if (response.isSuccessful()) {
            presenter.onLoadedMessages(response.body());
        } else if (response.code() == 401) {
            presenter.onUnauthorized();
        } else {
            presenter.onError();
        }
    }

    @Override
    public void onFailure(Call<List<Message>> call, Throwable t) {
        Log.d("Error", t.getMessage());
        presenter.onError();
    }

}