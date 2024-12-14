//package com.example.manwon;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageButton;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ChattingFragment extends Fragment {
//
//    private static final String ARG_CHAT_ROOM_ID = "chatRoomId"; // 전달받을 채팅방 ID 키
//
//    private EditText editTextMessage;
//    private ImageButton buttonSend;
//    private RecyclerView recyclerView;
//
//    private Chat_Adapter chatAdapter;
//    private List<ChatMessage> messages;
//    private DatabaseReference messagesRef;
//    private DatabaseReference chatRoomsRef;
//    private String chatRoomId;
//
//    // ChattingFragment 인스턴스 생성 메서드
//    public static ChattingFragment newInstance(String chatRoomId) {
//        ChattingFragment fragment = new ChattingFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_CHAT_ROOM_ID, chatRoomId);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_chat, container, false);
//
//        recyclerView = view.findViewById(R.id.recyclerView);
//        editTextMessage = view.findViewById(R.id.editTextMessage);
//        buttonSend = view.findViewById(R.id.buttonSend);
//
//        messages = new ArrayList<>();
//        chatAdapter = new Chat_Adapter(messages);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(chatAdapter);
//
//        // Bundle에서 전달된 채팅방 ID 가져오기
//        if (getArguments() != null) {
//            chatRoomId = getArguments().getString(ARG_CHAT_ROOM_ID);
//        }
//
//        // 채팅방 ID가 올바른지 확인
//        if (TextUtils.isEmpty(chatRoomId)) {
//            Log.e("ChattingFragment", "ChatRoomId is null or empty");
//            return view; // chatRoomId가 없는 경우 반환
//        }
//
//        // Firebase 참조 초기화
//        messagesRef = FirebaseDatabase.getInstance().getReference("Messages").child(chatRoomId);
//        chatRoomsRef = FirebaseDatabase.getInstance().getReference("ChatRooms");
//
//        // 메시지 로드
//        loadMessages();
//
//        // 메시지 전송 버튼 클릭 리스너
//        buttonSend.setOnClickListener(v -> {
//            String message = editTextMessage.getText().toString().trim();
//            if (!TextUtils.isEmpty(message)) {
//                sendMessage(message);
//                editTextMessage.setText("");
//            }
//        });
//
//        return view;
//    }
//
//    private void loadMessages() {
//        messagesRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                messages.clear();
//                for (DataSnapshot data : snapshot.getChildren()) {
//                    ChatMessage chatMessage = data.getValue(ChatMessage.class);
//                    if (chatMessage != null) {
//                        messages.add(chatMessage);
//                    }
//                }
//                chatAdapter.notifyDataSetChanged();
//                if (!messages.isEmpty()) {
//                    recyclerView.scrollToPosition(messages.size() - 1); // 마지막 메시지로 스크롤
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("ChattingFragment", "Failed to load messages", error.toException());
//            }
//        });
//    }
//
//    private void sendMessage(String message) {
//        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        if (TextUtils.isEmpty(senderUid)) {
//            Log.e("ChattingFragment", "User is not authenticated");
//            return;
//        }
//
//        long timestamp = System.currentTimeMillis();
//        ChatMessage chatMessage = new ChatMessage(senderUid, message, timestamp);
//
//        // 메시지를 Firebase에 저장
//        messagesRef.push().setValue(chatMessage).addOnSuccessListener(aVoid -> {
//            updateChatRoom(senderUid, message, timestamp);
//        }).addOnFailureListener(e -> Log.e("ChattingFragment", "Failed to send message", e));
//    }
//
//    private void updateChatRoom(String senderUid, String lastMessage, long timestamp) {
//        // giftcard/gifts 경로에서 아이템 제목 가져오기
//        DatabaseReference giftsRef = FirebaseDatabase.getInstance().getReference("giftcard").child("gifts");
//
//        giftsRef.orderByChild("sellerUid").equalTo(senderUid).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                // 아이템 제목을 저장할 final 변수
//                final String[] itemTitleHolder = {"제목 없음"}; // 기본값 설정
//
//                for (DataSnapshot data : snapshot.getChildren()) {
//                    // 첫 번째 일치 항목의 제목 가져오기
//                    String fetchedTitle = data.child("title").getValue(String.class);
//                    if (fetchedTitle != null) {
//                        itemTitleHolder[0] = fetchedTitle;
//                    }
//                    break;
//                }
//
//                // ChatRoom 업데이트
//                String itemTitle = itemTitleHolder[0];
//                ChatRoom chatRoom = new ChatRoom(chatRoomId, lastMessage, timestamp, senderUid, itemTitle);
//                chatRoomsRef.child(chatRoomId).setValue(chatRoom).addOnSuccessListener(aVoid -> {
//                    Log.d("ChattingFragment", "ChatRoom updated successfully with item title: " + itemTitle);
//                }).addOnFailureListener(e -> Log.e("ChattingFragment", "Failed to update ChatRoom", e));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("ChattingFragment", "Failed to fetch item title", error.toException());
//            }
//        });
//    }
//
//}


package com.example.manwon;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChattingFragment extends Fragment {

    private static final String ARG_CHAT_ROOM_ID = "chatRoomId";

    private EditText editTextMessage;
    private ImageButton buttonSend;
    private RecyclerView recyclerView;

    private Chat_Adapter chatAdapter;
    private List<ChatMessage> messages;
    private DatabaseReference messagesRef;
    private DatabaseReference chatRoomsRef;
    private String chatRoomId;

    public static ChattingFragment newInstance(String chatRoomId) {
        ChattingFragment fragment = new ChattingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CHAT_ROOM_ID, chatRoomId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chat, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSend = view.findViewById(R.id.buttonSend);

        messages = new ArrayList<>();
        chatAdapter = new Chat_Adapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatAdapter);

        if (getArguments() != null) {
            chatRoomId = getArguments().getString(ARG_CHAT_ROOM_ID);
        }

        if (TextUtils.isEmpty(chatRoomId)) {
            Log.e("ChattingFragment", "ChatRoomId is null or empty");
            return view;
        }

        messagesRef = FirebaseDatabase.getInstance().getReference("Messages").child(chatRoomId);
        chatRoomsRef = FirebaseDatabase.getInstance().getReference("ChatRooms");

        loadMessages();

        buttonSend.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString().trim();
            if (!TextUtils.isEmpty(message)) {
                sendMessage(message);
                editTextMessage.setText("");
            }
        });

        return view;
    }

    private void loadMessages() {
        messagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    ChatMessage chatMessage = data.getValue(ChatMessage.class);
                    if (chatMessage != null) {
                        messages.add(chatMessage);
                    }
                }
                chatAdapter.notifyDataSetChanged();
                if (!messages.isEmpty()) {
                    recyclerView.scrollToPosition(messages.size() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ChattingFragment", "Failed to load messages", error.toException());
            }
        });
    }

    private void sendMessage(String message) {
        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (TextUtils.isEmpty(senderUid)) {
            Log.e("ChattingFragment", "User is not authenticated");
            return;
        }

        long timestamp = System.currentTimeMillis();
        ChatMessage chatMessage = new ChatMessage(senderUid, message, timestamp);

        messagesRef.push().setValue(chatMessage).addOnSuccessListener(aVoid -> {
            updateChatRoom(senderUid, message, timestamp);
        }).addOnFailureListener(e -> Log.e("ChattingFragment", "Failed to send message", e));
    }

    private void updateChatRoom(String senderUid, String lastMessage, long timestamp) {
        Map<String, Object> chatRoomData = new HashMap<>();
        chatRoomData.put("roomId", chatRoomId);
        chatRoomData.put("lastMessage", lastMessage);
        chatRoomData.put("lastMessageTime", timestamp);
        chatRoomData.put("participantUid", senderUid);
        chatRoomData.put("itemTitle", "채팅방 제목"); // 필요시 제목 로직 추가

        chatRoomsRef.child(chatRoomId).updateChildren(chatRoomData).addOnSuccessListener(aVoid -> {
            Log.d("ChattingFragment", "ChatRoom updated successfully");
        }).addOnFailureListener(e -> Log.e("ChattingFragment", "Failed to update ChatRoom", e));
    }
}
