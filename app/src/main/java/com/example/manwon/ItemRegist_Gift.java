package com.example.manwon;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ItemRegist_Gift extends AppCompatActivity {

    // 뷰 선언
    private ImageButton backButton, uploadImageButton;
    private EditText titleEditText, itemTypeEditText, expirationDateEditText, detailsEditText, preferredCouponEditText;
    private TextView saveTempText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_regist_gift); // XML 파일 연결

        // 뷰 초기화
        backButton = findViewById(R.id.back_button);
        uploadImageButton = findViewById(R.id.gift_upload_image);
        titleEditText = findViewById(R.id.gift_title);
        itemTypeEditText = findViewById(R.id.gift_itemtype1);
        expirationDateEditText = findViewById(R.id.gift_expiration_date);
        detailsEditText = findViewById(R.id.gift_details);
        preferredCouponEditText = findViewById(R.id.gift_preferred_coupon);
        saveTempText = findViewById(R.id.gift_back_up);

        // 뒤로가기 버튼 클릭 이벤트
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });

        // 임시저장 텍스트 클릭 이벤트
        saveTempText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTempData();
            }
        });

        // 이미지 업로드 버튼 클릭 이벤트
        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    // 임시저장 동작 구현
    private void saveTempData() {
        String title = titleEditText.getText().toString();
        String itemType = itemTypeEditText.getText().toString();
        String expirationDate = expirationDateEditText.getText().toString();
        String details = detailsEditText.getText().toString();
        String preferredCoupon = preferredCouponEditText.getText().toString();

        // 임시로 Toast 메시지로 확인
        Toast.makeText(this, "임시저장 완료\n제목: " + title, Toast.LENGTH_SHORT).show();

        // 여기에서 SharedPreferences 또는 데이터베이스에 저장하는 로직 추가 가능
    }

    // 이미지 업로드 동작 구현 (미구현 상태)
    private void uploadImage() {
        // TODO: 갤러리에서 이미지 선택 또는 카메라 촬영 기능 구현
        Toast.makeText(this, "이미지 업로드 버튼 클릭됨", Toast.LENGTH_SHORT).show();
    }
}