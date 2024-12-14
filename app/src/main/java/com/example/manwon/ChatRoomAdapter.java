package com.example.manwon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// ChatRoomAdapter: RecyclerView 어댑터
public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ChatRoomViewHolder> {

    private List<ChatRoom> chatRoomList; // 채팅방 데이터 리스트
    private OnChatRoomClickListener listener; // 클릭 이벤트를 처리할 리스너

    // 클릭 이벤트 처리를 위한 인터페이스
    public interface OnChatRoomClickListener {
        void onChatRoomClick(ChatRoom chatRoom);
    }

    // ChatRoomAdapter 생성자
    public ChatRoomAdapter(List<ChatRoom> chatRoomList, OnChatRoomClickListener listener) {
        this.chatRoomList = chatRoomList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // item_chat_room 레이아웃을 뷰로 인플레이트
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_room, parent, false);
        return new ChatRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        // 현재 위치의 ChatRoom 데이터 가져오기
        ChatRoom chatRoom = chatRoomList.get(position);
        holder.bind(chatRoom, listener); // 데이터를 뷰에 바인딩
    }

    @Override
    public int getItemCount() {
        // 데이터 리스트의 크기를 반환
        return chatRoomList.size();
    }

    // ViewHolder: RecyclerView의 각 항목을 관리
    static class ChatRoomViewHolder extends RecyclerView.ViewHolder {

        private TextView tvChatRoomName;
        private TextView tvLastMessage;
        private TextView tvChatRoomTime;

        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            // item_chat_room.xml의 뷰 초기화
            tvChatRoomName = itemView.findViewById(R.id.tv_chat_room_name);
            tvLastMessage = itemView.findViewById(R.id.tv_last_message);
            tvChatRoomTime = itemView.findViewById(R.id.tv_chat_room_time);
        }

        public void bind(ChatRoom chatRoom, OnChatRoomClickListener listener) {
            // 데이터를 뷰에 설정
            tvChatRoomName.setText(chatRoom.getRoomName());
            tvLastMessage.setText(chatRoom.getLastMessage());
            tvChatRoomTime.setText(chatRoom.getTime());

            // 클릭 이벤트 처리
            itemView.setOnClickListener(v -> listener.onChatRoomClick(chatRoom));
        }
    }
}
