//
//
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
//import android.widget.TextView;
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
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ChattingFragment extends Fragment {
//
//    private static final String ARG_CHAT_ROOM_ID = "chatRoomId";
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
//
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
//        if (getArguments() != null) {
//            chatRoomId = getArguments().getString(ARG_CHAT_ROOM_ID);
//        }
//
//        if (TextUtils.isEmpty(chatRoomId)) {
//            Log.e("ChattingFragment", "ChatRoomId is null or empty");
//            return view;
//        }
//
//        messagesRef = FirebaseDatabase.getInstance().getReference("Messages").child(chatRoomId);
//        chatRoomsRef = FirebaseDatabase.getInstance().getReference("ChatRooms");
//
//        loadMessages();
//
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
//                    recyclerView.scrollToPosition(messages.size() - 1);
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
//        messagesRef.push().setValue(chatMessage).addOnSuccessListener(aVoid -> {
//            updateChatRoom(senderUid, message, timestamp);
//        }).addOnFailureListener(e -> Log.e("ChattingFragment", "Failed to send message", e));
//    }
//
//    private void updateChatRoom(String senderUid, String lastMessage, long timestamp) {
//        // Firebase에서 sellerUid를 기반으로 nickname 가져오기
//        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(senderUid);
//
//        userRef.child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String nickname = snapshot.getValue(String.class);
//                if (nickname == null || nickname.isEmpty()) {
//                    nickname = "알 수 없는 사용자"; // 기본값
//                }
//
//                // 채팅방 데이터 업데이트
//                Map<String, Object> chatRoomData = new HashMap<>();
//                chatRoomData.put("roomId", chatRoomId);
//                chatRoomData.put("lastMessage", lastMessage);
//                chatRoomData.put("lastMessageTime", timestamp);
//                chatRoomData.put("participantUid", senderUid);
//                chatRoomData.put("nickname", nickname); // 닉네임 추가
//                chatRoomData.put("itemTitle", "채팅방 제목"); // 필요시 제목 로직 추가
//
//                chatRoomsRef.child(chatRoomId).updateChildren(chatRoomData).addOnSuccessListener(aVoid -> {
//                    Log.d("ChattingFragment", "ChatRoom updated successfully with nickname: " + nickname);
//                }).addOnFailureListener(e -> Log.e("ChattingFragment", "Failed to update ChatRoom", e));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("ChattingFragment", "Failed to fetch nickname", error.toException());
//            }
//        });
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        // UI 요소 초기화
//        TextView chatRoomTitle = view.findViewById(R.id.nickname);
//
//        // Firebase에서 닉네임 가져오기
//        DatabaseReference chatRoomRef = chatRoomsRef.child(chatRoomId);
//
//        chatRoomRef.child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String nickname = snapshot.getValue(String.class);
//                chatRoomTitle.setText(nickname != null ? nickname : "알 수 없는 사용자");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("ChattingFragment", "Failed to fetch chat room nickname", error.toException());
//            }
//        });
//    }
//
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
import android.widget.TextView;

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
    private static final String ARG_SELLER_UID = "sellerUid";

    private EditText editTextMessage;
    private ImageButton buttonSend, backButton; // 백버튼 추가
    private RecyclerView recyclerView;
    private TextView nicknameTextView;

    private Chat_Adapter chatAdapter;
    private List<ChatMessage> messages;
    private DatabaseReference messagesRef;
    private DatabaseReference chatRoomsRef;
    private DatabaseReference usersRef;
    private String chatRoomId;
    private String sellerUid;
    private String senderNickname; // 현재 사용자의 닉네임
    private String itemTitle;

    public static ChattingFragment newInstance(String chatRoomId, String sellerUid, String itemTitle) {
        ChattingFragment fragment = new ChattingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CHAT_ROOM_ID, chatRoomId);
        args.putString(ARG_SELLER_UID, sellerUid);
        args.putString("itemTitle", itemTitle);
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
        backButton = view.findViewById(R.id.regist_back_button); // 백버튼 초기화
        nicknameTextView = view.findViewById(R.id.nickname);

        messages = new ArrayList<>();
        chatAdapter = new Chat_Adapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatAdapter);

        if (getArguments() != null) {
            chatRoomId = getArguments().getString(ARG_CHAT_ROOM_ID);
            sellerUid = getArguments().getString(ARG_SELLER_UID);
            itemTitle = getArguments().getString("itemTitle");
        }

        if (TextUtils.isEmpty(chatRoomId)) {
            Log.e("ChattingFragment", "ChatRoomId is null or empty");
            return view;
        }

        messagesRef = FirebaseDatabase.getInstance().getReference("Messages").child(chatRoomId);
        chatRoomsRef = FirebaseDatabase.getInstance().getReference("ChatRooms");
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        loadSenderNickname(); // 현재 사용자의 닉네임을 로드
        loadMessages();
        loadSellerNickname(); // 등록자의 닉네임을 가져와 TextView에 표시

        buttonSend.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString().trim();
            if (!TextUtils.isEmpty(message)) {
                sendMessage(message);
                editTextMessage.setText("");
            }
        });

        // 백버튼 동작 추가
        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
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

    private void loadSellerNickname() {
        if (TextUtils.isEmpty(sellerUid)) {
            Log.e("ChattingFragment", "SellerUid is null or empty");
            nicknameTextView.setText("알 수 없는 사용자");
            return;
        }

        usersRef.child(sellerUid).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nickname = snapshot.getValue(String.class);
                if (nickname != null && !nickname.isEmpty()) {
                    nicknameTextView.setText(nickname);
                } else {
                    nicknameTextView.setText("알 수 없는 사용자");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ChattingFragment", "Failed to load seller nickname", error.toException());
                nicknameTextView.setText("알 수 없는 사용자");
            }
        });
    }

    private void loadSenderNickname() {
        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (TextUtils.isEmpty(currentUserUid)) {
            Log.e("ChattingFragment", "Current user UID is null or empty");
            return;
        }

        usersRef.child(currentUserUid).child("nickname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                senderNickname = snapshot.getValue(String.class);
                if (TextUtils.isEmpty(senderNickname)) {
                    senderNickname = "알 수 없는 사용자";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ChattingFragment", "Failed to load sender nickname", error.toException());
                senderNickname = "알 수 없는 사용자";
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
        ChatMessage chatMessage = new ChatMessage(senderUid, message, timestamp, senderNickname);

        messagesRef.push().setValue(chatMessage).addOnSuccessListener(aVoid -> {
            updateChatRoom(senderUid, message, timestamp, itemTitle);
        }).addOnFailureListener(e -> Log.e("ChattingFragment", "Failed to send message", e));
    }

    private void updateChatRoom(String senderUid, String lastMessage, long timestamp, String itemTitle) {
        Map<String, Object> chatRoomData = new HashMap<>();
        chatRoomData.put("roomId", chatRoomId);
        chatRoomData.put("lastMessage", lastMessage);
        chatRoomData.put("lastMessageTime", timestamp);
        chatRoomData.put("participantUid", senderUid);
        chatRoomData.put("itemTitle", itemTitle);

        chatRoomsRef.child(chatRoomId).updateChildren(chatRoomData).addOnSuccessListener(aVoid -> {
            Log.d("ChattingFragment", "ChatRoom updated successfully");
        }).addOnFailureListener(e -> Log.e("ChattingFragment", "Failed to update ChatRoom", e));
    }
}

