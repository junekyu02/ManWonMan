//package com.example.manwon;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.auth.FirebaseAuth;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class Chat_Adapter extends RecyclerView.Adapter<Chat_Adapter.ChatViewHolder> {
//
//    private final List<ChatMessage> messages;
//
//    public Chat_Adapter(List<ChatMessage> messages) {
//        this.messages = messages;
//    }
//
//    public static class ChatViewHolder extends RecyclerView.ViewHolder {
//        TextView sentMessage, receivedMessage, sentTimestamp, receivedTimestamp;
//
//        public ChatViewHolder(@NonNull View itemView) {
//            super(itemView);
//            sentMessage = itemView.findViewById(R.id.text_sent_message);
//            receivedMessage = itemView.findViewById(R.id.text_received_message);
//            sentTimestamp = itemView.findViewById(R.id.text_sent_timestamp);
//            receivedTimestamp = itemView.findViewById(R.id.text_received_timestamp);
//        }
//    }
//
//    @NonNull
//    @Override
//    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.activity_chat_message, parent, false);
//        return new ChatViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
//        ChatMessage chatMessage = messages.get(position);
//
//        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        boolean isSentByCurrentUser = chatMessage.getSenderUid().equals(currentUserUid);
//
//        holder.sentMessage.setVisibility(View.GONE);
//        holder.receivedMessage.setVisibility(View.GONE);
//        holder.sentTimestamp.setVisibility(View.GONE);
//        holder.receivedTimestamp.setVisibility(View.GONE);
//
//        String formattedTime = formatTimestamp(chatMessage.getTimestamp());
//
//        if (isSentByCurrentUser) {
//            holder.sentMessage.setVisibility(View.VISIBLE);
//            holder.sentTimestamp.setVisibility(View.VISIBLE);
//            holder.sentMessage.setText(chatMessage.getMessage());
//            holder.sentTimestamp.setText(formattedTime);
//        } else {
//            holder.receivedMessage.setVisibility(View.VISIBLE);
//            holder.receivedTimestamp.setVisibility(View.VISIBLE);
//            holder.receivedMessage.setText(chatMessage.getMessage());
//            holder.receivedTimestamp.setText(formattedTime);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return messages.size();
//    }
//
//    private String formatTimestamp(long timestamp) {
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
//        return sdf.format(new Date(timestamp));
//    }
//}

package com.example.manwon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Chat_Adapter extends RecyclerView.Adapter<Chat_Adapter.ChatViewHolder> {

    private final List<ChatMessage> chatMessages;

    public Chat_Adapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
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
        ChatMessage chatMessage = chatMessages.get(position);

        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        boolean isSentByCurrentUser = chatMessage.getSenderUid().equals(currentUserUid);

        holder.sentLayout.setVisibility(View.GONE);
        holder.receivedLayout.setVisibility(View.GONE);

        String formattedTime = formatTimestamp(chatMessage.getTimestamp());

        if (isSentByCurrentUser) {
            holder.sentLayout.setVisibility(View.VISIBLE);
            holder.sentMessage.setText(chatMessage.getMessage());
            holder.sentTimestamp.setText(formattedTime);
        } else {
            holder.receivedLayout.setVisibility(View.VISIBLE);
            holder.receivedMessage.setText(chatMessage.getMessage());
            holder.receivedTimestamp.setText(formattedTime);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    private String formatTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView sentMessage, receivedMessage, sentTimestamp, receivedTimestamp;
        View sentLayout, receivedLayout;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            sentLayout = itemView.findViewById(R.id.sent_message_layout);
            receivedLayout = itemView.findViewById(R.id.received_message_layout);
            sentMessage = itemView.findViewById(R.id.text_sent_message);
            receivedMessage = itemView.findViewById(R.id.text_received_message);
            sentTimestamp = itemView.findViewById(R.id.text_sent_timestamp);
            receivedTimestamp = itemView.findViewById(R.id.text_received_timestamp);
        }
    }
}
