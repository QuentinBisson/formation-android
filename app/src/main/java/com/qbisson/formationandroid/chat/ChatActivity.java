package com.qbisson.formationandroid.chat;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qbisson.formationandroid.R;
import com.qbisson.formationandroid.SettingUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity implements ChatView {

    @BindView(R.id.messages)
    RecyclerView messageView;
    @BindView(R.id.message_input)
    EditText messageInput;
    @BindView(R.id.messagesView)
    SwipeRefreshLayout swipeRefreshLayout;
    private ChatPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        presenter = new ChatPresenter(this);

        presenter.onCreate(getSharedPreferences(SettingUtils.USER_PREFERENCES, MODE_PRIVATE));

        messageView.setHasFixedSize(false);
        messageView.setLayoutManager(new LinearLayoutManager(this));
        messageView.setAdapter(presenter.getAdapter());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                presenter.loadMessages();
            }

        });
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();

        super.onDestroy();
    }

    @OnClick(R.id.message_button)
    public void onChat(View view) {
        presenter.sendMessage(messageInput.getText().toString());
    }

    @Override
    public void onSentMessage() {
        messageInput.setText("");
        messageInput.clearFocus();
        messageView.scrollToPosition(presenter.getAdapter().getItemCount() - 1);
    }

    @Override
    public void onUnauthorized() {
        Toast.makeText(this, "Unauthorized access !", Toast.LENGTH_SHORT);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Could not connect to server !", Toast.LENGTH_SHORT);
    }
}