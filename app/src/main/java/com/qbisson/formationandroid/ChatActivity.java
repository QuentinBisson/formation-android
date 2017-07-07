package com.qbisson.formationandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.FrameLayout;

import com.qbisson.formationandroid.adapter.MessageAdapter;
import com.qbisson.formationandroid.models.Message;
import com.qbisson.formationandroid.services.ChatService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private ChatService chatService;
    private FrameLayout progressBarHolder;
    private RecyclerView list;
    private MessageAdapter adapter;
    private String login;
    private String password;

    public ChatActivity() {
        chatService = new ChatService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        login = intent.getStringExtra("login");
        password = intent.getStringExtra("password");
        //progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
//        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
//        animation.setDuration(200);
//        progressBarHolder.setAnimation(animation);
//        progressBarHolder.setVisibility(View.VISIBLE);

        list = (RecyclerView) findViewById(R.id.messages);
        list.setHasFixedSize(true);

        // use a linear layout manager
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter();
        list.setAdapter(adapter);
        loadMessages();
    }

    private void loadMessages() {
        Call<List<Message>> call = chatService.getMessages(login, password);
        call.enqueue(new retrofit2.Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
//                clearAnimation();
                if (response.code() == 200) {
                    // specify an adapter
                    adapter.setMessages(response.body());
                    // TODO populate view
                } else if (response.code() == 401) {
                    // TODO login problem
                } else {
                    // TODO Show potential error
                }
            }

            private void clearAnimation() {
                AlphaAnimation animation = new AlphaAnimation(0f, 1f);
                animation.setDuration(200);
                progressBarHolder.setAnimation(animation);
                progressBarHolder.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.d("Error", t.getMessage());
//                clearAnimation();
                // TODO Show potential error
            }
        });
    }

    private void addMessage(final String content) {
        Call<Void> call = chatService.addMessages(login, password, Message.builder().message(content).login(login).uuid(UUID.randomUUID().toString()).build());
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
//                clearAnimation();
                if (response.code() == 200) {
                    // TODO populate view
                } else if (response.code() == 400) {
                    // We retry to add the message till the UUID is available
                    addMessage(content);
                } else if (response.code() == 401) {
                    // TODO login problem
                } else {
                    // TODO Show potential error
                }
            }

            private void clearAnimation() {
                AlphaAnimation animation = new AlphaAnimation(0f, 1f);
                animation.setDuration(200);
                progressBarHolder.setAnimation(animation);
                progressBarHolder.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Error", t.getMessage());
//                clearAnimation();
                // TODO Show potential error
            }
        });
    }
}
