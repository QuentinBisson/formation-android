package com.qbisson.formationandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qbisson.formationandroid.R;
import com.qbisson.formationandroid.models.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> messages;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MessageAdapter() {
        this.messages = new ArrayList<>();
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.login.setText(message.getLogin());
        holder.message.setText(message.getMessage());
        if (message.getLogin().equals("qbisson")) {
            holder.login.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.message.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return messages.size();
    }

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView login;
        public TextView message;

        public ViewHolder(View v) {
            super(v);
            login = v.findViewById(R.id.login);
            message = v.findViewById(R.id.message);
        }
    }
}
