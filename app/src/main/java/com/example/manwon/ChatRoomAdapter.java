package com.example.manwon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ChatRoomViewHolder> {

    private List<ChatRoom> chatRoomList;
    private OnChatRoomClickListener listener;

    public interface OnChatRoomClickListener {
        void onChatRoomClick(ChatRoom chatRoom);
    }

    public ChatRoomAdapter(List<ChatRoom> chatRoomList, OnChatRoomClickListener listener) {
        this.chatRoomList = chatRoomList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_room, parent, false);
        return new ChatRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        ChatRoom chatRoom = chatRoomList.get(position);
        holder.bind(chatRoom, listener);
    }

    @Override
    public int getItemCount() {
        return chatRoomList.size();
    }

    static class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvChatRoomName;
        private final TextView tvLastMessage;
        private final TextView tvChatRoomTime;

        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChatRoomName = itemView.findViewById(R.id.tv_chat_room_name);
            tvLastMessage = itemView.findViewById(R.id.tv_last_message);
            tvChatRoomTime = itemView.findViewById(R.id.tv_chat_room_time);
        }

        public void bind(ChatRoom chatRoom, OnChatRoomClickListener listener) {
            String itemTitle = chatRoom.getItemTitle() != null ? chatRoom.getItemTitle() : "제목 없음"; // 기본값 처리
            tvChatRoomName.setText(itemTitle); // 아이템 제목을 표시
            tvLastMessage.setText(chatRoom.getLastMessage() != null ? chatRoom.getLastMessage() : "메시지가 없습니다."); // 메시지 표시
            tvChatRoomTime.setText(formatTimestamp(chatRoom.getLastMessageTime())); // 시간 표시

            itemView.setOnClickListener(v -> listener.onChatRoomClick(chatRoom));
        }

        private String formatTimestamp(long timestamp) {
            if (timestamp <= 0) return ""; // 유효하지 않은 시간 처리
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm", Locale.getDefault());
            return sdf.format(new Date(timestamp));
        }
    }
}
