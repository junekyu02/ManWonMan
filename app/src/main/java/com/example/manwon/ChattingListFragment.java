package com.example.manwon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ChattingListFragment extends Fragment {

    private RecyclerView recyclerViewChatList;
    private ChatRoomAdapter chatRoomAdapter;
    private List<ChatRoom> chatRoomList;

    public static ChattingListFragment newInstance() {
        return new ChattingListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatting_list, container, false);

        recyclerViewChatList = view.findViewById(R.id.recycler_view_chat_list);
        recyclerViewChatList.setLayoutManager(new LinearLayoutManager(getContext()));

        // 채팅방 목록 초기화
        chatRoomList = new ArrayList<>();
        chatRoomList.add(new ChatRoom("친구1", "안녕!", "오후 2:15"));
        chatRoomList.add(new ChatRoom("친구2", "오랜만이야", "오후 1:05"));
        chatRoomList.add(new ChatRoom("동료", "회의 준비 완료", "오전 9:45"));

        // 어댑터 설정
        chatRoomAdapter = new ChatRoomAdapter(chatRoomList, chatRoom -> {
            // 선택한 채팅방 이름을 전달하며 ChattingFragment로 이동
            Bundle bundle = new Bundle();
            bundle.putString("chatRoomName", chatRoom.getRoomName());

            ChattingFragment chattingFragment = ChattingFragment.newInstance();
            chattingFragment.setArguments(bundle);

            // Fragment 전환
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, chattingFragment)
                    .addToBackStack(null) // BackStack에 추가
                    .commit();
        });

        recyclerViewChatList.setAdapter(chatRoomAdapter);

        return view;
    }
}
