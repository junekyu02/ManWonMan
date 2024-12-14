package com.example.manwon;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Gift_Userchange_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_userchange);

        ImageButton backButton = findViewById(R.id.back_button);
        TextView titleText = findViewById(R.id.title_text);
        ImageView profileImage = findViewById(R.id.profile_image);
        TextView userName = findViewById(R.id.user_name);
        ImageView productImage = findViewById(R.id.product_image);
        ImageButton chattingButton = findViewById(R.id.chatting_buttonimage);

        backButton.setOnClickListener(v -> finish());

        userName.setText("홍길동");

        chattingButton.setOnClickListener(v -> {
            String sellerUid = "sellerUidExample"; // 글을 올린 사람의 UID
            String buyerUid = "buyerUidExample"; // 채팅을 요청한 사용자의 UID
            String chatRoomId = generateChatRoomId(sellerUid, buyerUid);

            backButton.setVisibility(View.GONE);
            titleText.setVisibility(View.GONE);
            profileImage.setVisibility(View.GONE);
            userName.setVisibility(View.GONE);
            productImage.setVisibility(View.GONE);
            chattingButton.setVisibility(View.GONE);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ChattingFragment.newInstance(chatRoomId))
                    .addToBackStack(null)
                    .commit();
        });
    }

    private String generateChatRoomId(String sellerUid, String buyerUid) {
        return sellerUid.compareTo(buyerUid) < 0 ? sellerUid + "_" + buyerUid : buyerUid + "_" + sellerUid;
    }
}
