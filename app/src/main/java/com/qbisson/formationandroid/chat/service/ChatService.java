package com.qbisson.formationandroid.chat.service;

import com.qbisson.formationandroid.SettingUtils;
import com.qbisson.formationandroid.model.Message;

import java.io.IOException;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatService {

    public Call<Void> login(String username, String password) {
        return newClient(username, password).login();
    }

    public Call<Void> register(String username, String password) {
        return newClient().register(username, password);
    }

    public Call<List<Message>> getMessages(String username, String password) {
        return newClient(username, password).getMessages();
    }

    public Call<Void> addMessage(String username, String password, Message message) {
        return newClient(username, password).addMessage(message);
    }

    private ChatServiceClient newClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SettingUtils.SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ChatServiceClient.class);
    }

    private ChatServiceClient newClient(final String username, final String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        return response.request().newBuilder()
                                .header("Authorization", Credentials.basic(username, password))
                                .build();
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SettingUtils.SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(ChatServiceClient.class);
    }

}
