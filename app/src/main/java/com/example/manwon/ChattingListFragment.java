package com.example.manwon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

public class ChattingListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatRoomAdapter chatRoomAdapter;
    private List<ChatRoom> chatRoomList;
    private DatabaseReference chatRoomsRef;
    private TextView emptyListMessage; // 채팅방이 없을 때 표시하는 메시지

    // 기본 newInstance() 메서드
    public static ChattingListFragment newInstance() {
        return new ChattingListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatting_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_chat_list);
        emptyListMessage = view.findViewById(R.id.empty_list_message); 
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chatRoomList = new ArrayList<>();
        chatRoomAdapter = new ChatRoomAdapter(chatRoomList, chatRoom -> {
            // 특정 채팅방 클릭 시 ChattingFragment로 이동
            ChattingFragment chattingFragment = ChattingFragment.newInstance(chatRoom.getRoomId(), chatRoom.getParticipantNickname());

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, chattingFragment)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(chatRoomAdapter);

        loadChatRooms();

        return view;
    }

    private void loadChatRooms() {
        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        chatRoomsRef = FirebaseDatabase.getInstance().getReference("ChatRooms");

        chatRoomsRef.orderByChild("participantUid").equalTo(currentUserUid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chatRoomList.clear();

                        for (DataSnapshot data : snapshot.getChildren()) {
                            ChatRoom chatRoom = data.getValue(ChatRoom.class);
                            if (chatRoom != null) { // 데이터가 유효한지 확인
                                chatRoomList.add(chatRoom);
                            }
                        }

                        // UI 업데이트
                        chatRoomAdapter.notifyDataSetChanged();

                        // 빈 목록 메시지 처리
                        if (chatRoomList.isEmpty()) {
                            emptyListMessage.setVisibility(View.VISIBLE); // 빈 목록 메시지 표시
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            emptyListMessage.setVisibility(View.GONE); // 빈 목록 메시지 숨김
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // 에러 로깅
                        emptyListMessage.setText("채팅방을 불러오는 중 문제가 발생했습니다.");
                        emptyListMessage.setVisibility(View.VISIBLE);
                    }
                });
    }
}
