package com.example.manwon;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
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
            ChattingFragment chattingFragment = ChattingFragment.newInstance(chatRoom.getRoomId(), chatRoom.getParticipantUid(), chatRoom.getItemTitle());

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, chattingFragment)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(chatRoomAdapter);

        loadChatRooms();

        // PopupWindow 초기화
        LayoutInflater popupInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View popupView = popupInflater.inflate(R.layout.activity_popup_chat, null);
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        // PopupWindow 설정
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        // PopupWindow에 그림자 효과 추가
        ViewCompat.setElevation(popupView, 8f); // 필요에 따라 그림자 높이 조정

        // helpImageView2를 눌렀을 때 PopupWindow 표시
        View helpImageView2 = view.findViewById(R.id.popup_text_chat); // fragment_chatting_list.xml에 정의된 helpImageView2
        helpImageView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // PopupWindow 표시
                    popupWindow.showAsDropDown(v, 0, 0);
                    return true;
                }
                return false;
            }
        });

        // 배경 터치 시 PopupWindow 닫기
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
                return false;
            }
        });

        return view;
    }


    public void showDeleteDialog(Context context, int position, GongguItem_Adapter adapter) {
        new AlertDialog.Builder(context)
                .setTitle("삭제 확인")
                .setMessage("해당 아이템을 삭제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> {
                    adapter.removeItem(position);
                    Toast.makeText(context, "아이템이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("취소", (dialog, which) -> adapter.notifyItemChanged(position))
                .show();
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
