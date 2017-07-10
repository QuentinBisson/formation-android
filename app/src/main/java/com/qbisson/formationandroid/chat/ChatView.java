package com.qbisson.formationandroid.chat;

public interface ChatView {
    void onSentMessage();

    void onUnauthorized();

    void onError();
}
