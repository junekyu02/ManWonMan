package com.example.manwon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ItemRegist_Gift extends AppCompatActivity {

    // 뷰 선언
    private ImageButton backButton, uploadImageButton; // changeButton 추가
    private EditText titleEditText, itemTypeEditText, expirationDateEditText, detailsEditText;
    private TextView saveTempText, preferredCouponText, changeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_regist_gift); // XML 파일 연결

        // 뷰 초기화
        backButton = findViewById(R.id.back_button);
        uploadImageButton = findViewById(R.id.gift_upload_image);
        titleEditText = findViewById(R.id.gift_title);
        changeButton = findViewById(R.id.gift_itemtype1); // 교환할 유형 버튼
        expirationDateEditText = findViewById(R.id.gift_expiration_date);
        detailsEditText = findViewById(R.id.gift_details);
        preferredCouponText = findViewById(R.id.gift_preferred_coupon);
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

        // 교환할 유형 버튼 클릭 이벤트
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemRegist_Gift.this, Gift_Item_Regist_Catecory.class);
                startActivityForResult(intent, 200); // Gift_Item_Regist_Catecory 액티비티로 이동
            }
        });
        preferredCouponText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemRegist_Gift.this, Gift_Item_Regist_Catecory.class);
                startActivityForResult(intent, 201);
            }
        });
    }

    // 임시저장 동작 구현
    private void saveTempData() {
        String title = titleEditText.getText().toString();
        String itemType = itemTypeEditText.getText().toString();
        String expirationDate = expirationDateEditText.getText().toString();
        String details = detailsEditText.getText().toString();
        String preferredCoupon = preferredCouponText.getText().toString();

        // 임시로 Toast 메시지로 확인
        Toast.makeText(this, "임시저장 완료\n제목: " + title, Toast.LENGTH_SHORT).show();

        // 여기에서 SharedPreferences 또는 데이터베이스에 저장하는 로직 추가 가능
    }

    // 이미지 업로드 동작 구현 (미구현 상태)
    private void uploadImage() {
        // TODO: 갤러리에서 이미지 선택 또는 카메라 촬영 기능 구현
        Toast.makeText(this, "이미지 업로드 버튼 클릭됨", Toast.LENGTH_SHORT).show();
    }

    // onActivityResult() 메서드 오버라이드 (2번 액티비티로부터 결과 받기)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String resultText = data.getStringExtra("selectedText");  // 2번 액티비티에서 전달된 텍스트
            int selectedColor = data.getIntExtra("selectedColor", Color.BLACK);

            if (requestCode == 200) {
                // itemtype1 TextView 텍스트 변경
                TextView itemtype1 = findViewById(R.id.gift_itemtype1);
                itemtype1.setText(resultText);
                itemtype1.setTextColor(selectedColor);
            } else if (requestCode == 201) {
                // itemtype2 TextView 텍스트 변경
                TextView itemtype2 = findViewById(R.id.gift_preferred_coupon);
                itemtype2.setText(resultText);
                itemtype2.setTextColor(selectedColor);
            }
        }
    }

    // Submit 버튼 클릭 시 호출되는 메서드
    public void onSubmitButtonClick(View view) {
        // AlertDialog 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemRegist_Gift.this);

        builder.setIcon(R.drawable.registration_warning); // 다이얼로그 아이콘 설정
        builder.setTitle("정말 등록하시겠습니까?");

        builder.setMessage("빠진 부분이 있는지 반드시 확인해주세요.")  // 다이얼로그에 표시할 메시지
                .setCancelable(false)  // 다이얼로그 외부 터치 시 닫히지 않도록 설정
                .setPositiveButton("제출하기", (dialog, id) -> {
                    // 제출하기 버튼 클릭 시 다음 액티비티로 이동
                    Intent intent = new Intent(ItemRegist_Gift.this, ItemRegist_Finish.class);
                    startActivity(intent);  // 액티비티 전환
                    ItemRegist_Gift.this.finish();    // submit 버튼 클릭 시에 ItemRegist_Finish 액티비티로 넘어가면서 교환하기 등록 화면은 제거함
                })
                .setNegativeButton("검토하기", (dialog, id) -> {
                    // 취소 버튼 클릭 시 다이얼로그 닫기
                    dialog.dismiss();
                });

        // 다이얼로그 띄우기
        AlertDialog alert = builder.create();
        alert.show();
    }
}