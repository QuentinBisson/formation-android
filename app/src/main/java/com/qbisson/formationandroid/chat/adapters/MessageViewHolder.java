package com.qbisson.formationandroid.chat.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qbisson.formationandroid.R;
import com.qbisson.formationandroid.model.Message;

import butterknife.BindView;
import butterknife.ButterKnife;

class MessageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.message_card)
    CardView messageCard;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.message)
    TextView message;

    public MessageViewHolder(View v) {
        super(v);

        ButterKnife.bind(this, v);
    }

    public void fillMessage(Message msg, int gravity) {
        login.setText(msg.getLogin());
        message.setText(msg.getMessage());

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) messageCard.getLayoutParams();
        layoutParams.gravity = gravity;
    }
}