package com.qbisson.formationandroid.chat.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qbisson.formationandroid.R;
import com.qbisson.formationandroid.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private List<Message> messages;
    private String currentLogin;

    public MessageAdapter(String login) {
        this.messages = new ArrayList<>();
        this.currentLogin = login;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        this.notifyDataSetChanged();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.fillMessage(message, message.getLogin().equals(currentLogin) ? Gravity.END : Gravity.START);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}
