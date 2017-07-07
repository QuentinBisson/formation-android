package com.qbisson.formationandroid.services;

import com.qbisson.formationandroid.models.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatService {
    private static final String SERVICE_URL = "https://training.loicortola.com/chat-rest/1.0/";

    private ChatServiceClient client;

    public ChatService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        client = retrofit.create(ChatServiceClient.class);
    }

    public Call<Void> login(String username, String password) {
        return client.login(username, password);
    }

    public Call<List<Message>> getMessages(String username, String password) {
        return client.getMessages(username, password);
    }

    public Call<Void> addMessages(String username, String password, Message message) {
        return client.addMessages(username, password, message);
    }

}
