package com.example.manwon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Chat_Adapter extends RecyclerView.Adapter<Chat_Adapter.ChatViewHolder> {

    private final List<ChatMessage> messages;

    public Chat_Adapter(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView sentMessage, receivedMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            sentMessage = itemView.findViewById(R.id.text_sent_message);
            receivedMessage = itemView.findViewById(R.id.text_received_message);
        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage chatMessage = messages.get(position);

        // 보낸 메시지와 받은 메시지를 가시화
        holder.sentMessage.setVisibility(View.GONE);
        holder.receivedMessage.setVisibility(View.GONE);

        if (chatMessage.isSent()) {
            // 보낸 메시지
            holder.sentMessage.setVisibility(View.VISIBLE);
            holder.sentMessage.setText(chatMessage.getMessage());
        } else {
            // 받은 메시지
            holder.receivedMessage.setVisibility(View.VISIBLE);
            holder.receivedMessage.setText(chatMessage.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(ChatMessage chatMessage) {
        messages.add(chatMessage);
        notifyItemInserted(messages.size() - 1);
    }
}
