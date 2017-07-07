package com.qbisson.formationandroid.services;

import com.qbisson.formationandroid.models.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatServiceClient {

    @GET("connect/{login}/{password}")
    Call<Void> login(@Path("login") String login, @Path("password") String password);

    @GET("messages/{login}/{password}")
    Call<List<Message>> getMessages(@Path("login") String login, @Path("password") String password);

    @POST("messages/{login}/{password}")
    Call<Void> addMessages(@Path("login") String login, @Path("password") String password, @Body Message message);
}
