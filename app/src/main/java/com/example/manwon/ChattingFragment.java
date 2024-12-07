package com.example.manwon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChattingFragment extends Fragment {

    private EditText editTextMessage;
    private ImageButton buttonSend;
    private RecyclerView recyclerView;
    private TextView textViewNotification;
    private ImageButton backButton;

    private List<ChatMessage> messages;
    private Chat_Adapter chatAdapter;

    public ChattingFragment() {}

    public static ChattingFragment newInstance() {
        return new ChattingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // activity_chat.xml 레이아웃을 인플레이트
        View rootView = inflater.inflate(R.layout.activity_chat, container, false);

        // 뷰 참조
        editTextMessage = rootView.findViewById(R.id.editTextMessage);
        buttonSend = rootView.findViewById(R.id.buttonSend);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        textViewNotification = rootView.findViewById(R.id.textViewNotification);
        backButton = rootView.findViewById(R.id.regist_back_button);

        // 메시지 리스트와 어댑터 설정
        messages = new ArrayList<>();
        chatAdapter = new Chat_Adapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatAdapter);

        // 메시지 전송 버튼 클릭 리스너 설정
        buttonSend.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString();
            if (!message.isEmpty()) {
                // 내 메시지 추가 (isSent: true)
                messages.add(new ChatMessage(message, true));
                chatAdapter.notifyItemInserted(messages.size() - 1); // 새 메시지 추가
                recyclerView.scrollToPosition(messages.size() - 1); // 최신 메시지가 보이게 스크롤
                editTextMessage.setText(""); // 입력란 초기화
            }
        });

        // 뒤로 가기 버튼 클릭 리스너 설정

       backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 액티비티로 돌아가거나 프래그먼트 스택에서 뒤로 가기
                getActivity().onBackPressed(); // 뒤로 가기 버튼을 눌렀을 때 액티비티에서 처리
            }
        });

        // 알림 메시지 설정 (필요에 따라 다르게 설정 가능)
        textViewNotification.setText("채팅 전, 이용 수칙을 꼭 읽어주세요!\n서로서로 바르고 고운말로 소통해주세요.");

        return rootView;
    }
}
