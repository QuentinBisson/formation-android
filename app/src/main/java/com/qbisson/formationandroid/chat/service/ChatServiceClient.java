package com.qbisson.formationandroid.chat.service;

import com.qbisson.formationandroid.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ChatServiceClient {

    @GET("connect")
    Call<Void> login();

    @POST("register")
    @FormUrlEncoded
    Call<Void> register(@Field("login") String login, @Field("password") String password);

    @GET("messages")
    Call<List<Message>> getMessages();

    @POST("messages")
    Call<Void> addMessage(@Body Message message);
}
