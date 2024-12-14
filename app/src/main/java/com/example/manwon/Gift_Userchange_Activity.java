package com.example.manwon;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Gift_Userchange_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_userchange);

        // UI 초기화
        ImageButton backButton = findViewById(R.id.back_button);
        TextView titleText = findViewById(R.id.title_text);
        ImageView profileImage = findViewById(R.id.profile_image);
        TextView userName = findViewById(R.id.user_name);
        ImageView productImage = findViewById(R.id.product_image);
        TextView detailText = findViewById(R.id.user_writedetail); // 상세 설명 텍스트
        ImageButton chattingButton = findViewById(R.id.chatting_buttonimage);

        // 전달받은 데이터 가져오기
        String giftId = getIntent().getStringExtra("giftId");
        String sellerUid = getIntent().getStringExtra("sellerUid");
        String detail = getIntent().getStringExtra("detail");
        String itemTitle = getIntent().getStringExtra("itemTitle");

        // 닉네임 로드
        if (sellerUid != null) {
            loadSellerNickname(sellerUid, userName, titleText);
        }

        // 상세 설명 적용
        if (detail != null) {
            detailText.setText(detail);
        } else {
            detailText.setText("상세 설명이 없습니다.");
        }

        // 뒤로가기 버튼
        backButton.setOnClickListener(v -> finish());

        // 채팅 버튼 클릭 리스너
        chattingButton.setOnClickListener(v -> {
            String buyerUid = "currentBuyerUid"; // 현재 사용자의 UID (로그인된 사용자 UID 가져오기)
            String chatRoomId = generateChatRoomId(sellerUid, buyerUid);

            // UI 숨기고 채팅 화면 표시
            backButton.setVisibility(View.GONE);
            titleText.setVisibility(View.GONE);
            profileImage.setVisibility(View.GONE);
            userName.setVisibility(View.GONE);
            productImage.setVisibility(View.GONE);
            chattingButton.setVisibility(View.GONE);
            detailText.setVisibility(View.GONE);

            // 프래그먼트로 채팅 화면 전환
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ChattingFragment.newInstance(chatRoomId))
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void loadSellerNickname(String sellerUid, TextView userName, TextView titleText) {
        // Firebase에서 판매자의 닉네임 로드
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(sellerUid).child("nickname");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nickname = snapshot.getValue(String.class);
                    userName.setText(nickname != null ? nickname : "사용자");
                    titleText.setText(nickname != null ? nickname + "님이 등록한 물품" : "등록된 물품");
                } else {
                    userName.setText("사용자 정보를 불러올 수 없습니다.");
                    titleText.setText("등록된 물품");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                userName.setText("사용자 로드 실패: " + error.getMessage());
                titleText.setText("등록된 물품");
            }
        });
    }

    private String generateChatRoomId(String sellerUid, String buyerUid) {
        return sellerUid.compareTo(buyerUid) < 0 ? sellerUid + "_" + buyerUid : buyerUid + "_" + sellerUid;
    }
}
