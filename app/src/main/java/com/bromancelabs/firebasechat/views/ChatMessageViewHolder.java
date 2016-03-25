package com.bromancelabs.firebasechat.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bromancelabs.firebasechat.R;
import com.bromancelabs.firebasechat.models.ChatMessage;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChatMessageViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.txt_chat_name) TextView chatName;
    @Bind(R.id.txt_chat_message) TextView chatMessage;

    public ChatMessageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindChatMessage(ChatMessage message) {
        chatName.setText(message.getName());
        chatMessage.setText(message.getText());
    }
}
