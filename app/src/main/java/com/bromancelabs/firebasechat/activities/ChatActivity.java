package com.bromancelabs.firebasechat.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;

import com.bromancelabs.firebasechat.R;
import com.bromancelabs.firebasechat.models.ChatMessage;
import com.bromancelabs.firebasechat.views.ChatMessageViewHolder;
import com.bromancelabs.firebasechat.views.SimpleDividerItemDecoration;
import com.firebase.ui.FirebaseRecyclerAdapter;

import butterknife.Bind;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {

    @Bind(R.id.txt_name) EditText chatNameEditText;
    @Bind(R.id.txt_message) EditText chatMessageEditText;
    @Bind(R.id.chat_recyclerview) RecyclerView recyclerView;

    private FirebaseRecyclerAdapter<ChatMessage, ChatMessageViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void onResume() {
        super.onResume();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ChatMessage, ChatMessageViewHolder>
                (ChatMessage.class, R.layout.recyclerview_row_item, ChatMessageViewHolder.class, firebaseRef) {
            @Override
            protected void populateViewHolder(ChatMessageViewHolder chatMessageViewHolder, ChatMessage message, int i) {
                chatMessageViewHolder.bindChatMessage(message);
            }
        };

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, R.drawable.recyclerview_horizontal_divider));
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.cleanup();
        }
    }

    @OnClick(R.id.send_button)
    public void sendButtonClick() {
        String name = chatNameEditText.getText().toString();
        String message = chatMessageEditText.getText().toString();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(message)) {
            ChatMessage msg = new ChatMessage(name, message);
            firebaseRef.push().setValue(msg);
            chatNameEditText.setText("");
            chatMessageEditText.setText("");
        } else {
            Snackbar.make(recyclerView, "please input a valid name and message", Snackbar.LENGTH_SHORT).show();
        }
    }
}
