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
        setContentView(R.layout.activity_gift_userchange); // XML 파일 이름에 맞게 수정

        // 뷰 참조
        ImageButton backButton = findViewById(R.id.back_button);
        TextView titleText = findViewById(R.id.title_text);
        ImageView profileImage = findViewById(R.id.profile_image);
        TextView userName = findViewById(R.id.user_name);
        ImageView productImage = findViewById(R.id.product_image);
        ImageButton chattingButton = findViewById(R.id.chatting_buttonimage);

        // 뒤로가기 버튼 클릭 리스너
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 액티비티 종료
                finish();
            }
        });
        // 채팅 버튼 클릭 리스너
        chattingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 채팅 화면으로 이동하는 로직 추가 (예: Intent 사용)
                // Intent intent = new Intent(Gift_Userchange_Activity.this, ChatActivity.class);
                // startActivity(intent);
            }
        });

        // 프로필 이미지나 사용자 이름은 필요에 따라 동적으로 설정 가능
        userName.setText("홍길동"); // 예제 데이터

        chattingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 기존 UI 숨기기
                backButton.setVisibility(View.GONE);
                titleText.setVisibility(View.GONE);
                profileImage.setVisibility(View.GONE);
                userName.setVisibility(View.GONE);
                productImage.setVisibility(View.GONE);
                chattingButton.setVisibility(View.GONE);

                // ChattingFragment로 이동하는 로직
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ChattingFragment.newInstance()) // fragment_container는 액티비티에서 Fragment를 담을 컨테이너의 ID
                        .addToBackStack(null) // 뒤로가기 버튼으로 이전 화면으로 돌아갈 수 있도록 추가
                        .commit();
            }
        });
    }
}
