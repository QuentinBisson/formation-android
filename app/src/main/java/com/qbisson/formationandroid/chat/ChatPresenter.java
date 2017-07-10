package com.qbisson.formationandroid.chat;

import android.content.SharedPreferences;

import com.qbisson.formationandroid.SettingUtils;
import com.qbisson.formationandroid.chat.adapters.MessageAdapter;
import com.qbisson.formationandroid.chat.service.ChatService;
import com.qbisson.formationandroid.chat.service.ChatSocketManager;
import com.qbisson.formationandroid.model.Message;

import java.util.List;
import java.util.UUID;

import io.socket.client.Socket;
import retrofit2.Call;

class ChatPresenter {

    private ChatSocketManager socketManager;
    private ChatView view;

    private String login;
    private String password;

    private MessageAdapter adapter;
    private ChatService chatService;
    private Socket socket;

    ChatPresenter(ChatView view) {
        this.view = view;
        chatService = new ChatService();
    }

    void onCreate(SharedPreferences preferences) {
        login = preferences.getString(SettingUtils.LOGIN_PREFERENCE, "");
        password = preferences.getString(SettingUtils.PASSWORD_PREFERENCE, "");

        this.adapter = new MessageAdapter(login);
        this.loadMessages();

        ChatSocketManager.getInstance().connect();
    }

    void onDestroy() {
        login = null;
        password = null;
        ChatSocketManager.getInstance().closeSocket();
        view = null;
    }

    MessageAdapter getAdapter() {
        return adapter;
    }

    void loadMessages() {
        Call<List<Message>> call = chatService.getMessages(login, password);
        call.enqueue(new LoadMessagesCallback(this));
    }

    void sendMessage(String content) {
        if (!content.isEmpty()) {
            Call<Void> call = chatService.addMessage(login, password, Message.builder().message(content).login(login).uuid(UUID.randomUUID().toString()).build());
            call.enqueue(new SendMessageCallback(this, content));
        }
    }

    void onLoadedMessages(List<Message> messages) {
        adapter.setMessages(messages);
    }

    void onSentMessage() {
        view.onSentMessage();
        loadMessages();
    }

    void onUnauthorized() {
        view.onUnauthorized();
    }

    void onError() {
        view.onError();
    }

}
