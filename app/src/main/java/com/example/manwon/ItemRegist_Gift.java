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

// Firebase 관련 import
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ItemRegist_Gift extends AppCompatActivity {

    private ImageButton backButton, uploadImageButton;
    private EditText titleEditText, expirationDateEditText, detailsEditText;
    private TextView saveTempText, preferredCouponText, changeButton;

    // Firebase Database 참조
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_regist_gift);

        // Firebase Database 초기화
        mDatabase = FirebaseDatabase.getInstance().getReference();

        backButton = findViewById(R.id.back_button);
        uploadImageButton = findViewById(R.id.gift_upload_image);
        titleEditText = findViewById(R.id.gift_title);
        changeButton = findViewById(R.id.gift_itemtype1);
        expirationDateEditText = findViewById(R.id.gift_expiration_date);
        detailsEditText = findViewById(R.id.gift_details);
        preferredCouponText = findViewById(R.id.gift_preferred_coupon);
        saveTempText = findViewById(R.id.gift_back_up);

        // 뒤로가기 버튼
        backButton.setOnClickListener(v -> finish());

        // 임시저장
        saveTempText.setOnClickListener(v -> saveTempData());

        // 이미지 업로드(미구현)
        uploadImageButton.setOnClickListener(v -> uploadImage());

        // 교환할 유형 선택
        changeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ItemRegist_Gift.this, Gift_Item_Regist_Catecory.class);
            startActivityForResult(intent, 200);
        });

        // 희망 쿠폰 선택
        preferredCouponText.setOnClickListener(view -> {
            Intent intent = new Intent(ItemRegist_Gift.this, Gift_Item_Regist_Catecory.class);
            startActivityForResult(intent, 201);
        });
    }

    private void saveTempData() {
        String title = titleEditText.getText().toString();
        String expirationDate = expirationDateEditText.getText().toString();
        String details = detailsEditText.getText().toString();
        String preferredCoupon = preferredCouponText.getText().toString();
        // 유형은 changeButton Text
        String itemType = changeButton.getText().toString();

        // 임시로 Toast
        Toast.makeText(this, "임시저장 완료\n제목: " + title, Toast.LENGTH_SHORT).show();
        // 필요하다면 SharedPreferences에 저장하는 로직 추가
    }

    private void uploadImage() {
        Toast.makeText(this, "이미지 업로드 버튼 클릭됨", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String resultText = data.getStringExtra("selectedText");
            int selectedColor = data.getIntExtra("selectedColor", Color.BLACK);

            if (requestCode == 200) {
                // itemtype1 TextView에 반영
                TextView itemtype1 = findViewById(R.id.gift_itemtype1);
                itemtype1.setText(resultText);
                itemtype1.setTextColor(selectedColor);
            } else if (requestCode == 201) {
                // preferred_coupon TextView에 반영
                TextView itemtype2 = findViewById(R.id.gift_preferred_coupon);
                itemtype2.setText(resultText);
                itemtype2.setTextColor(selectedColor);
            }
        }
    }

    public void onSubmitButtonClick(View view) {
        // AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemRegist_Gift.this);
        builder.setIcon(R.drawable.registration_warning);
        builder.setTitle("정말 등록하시겠습니까?");
        builder.setMessage("빠진 부분이 있는지 반드시 확인해주세요.")
                .setCancelable(false)
                .setPositiveButton("제출하기", (dialog, id) -> {
                    // 제출하기 버튼 클릭 시 Firebase에 데이터 등록 후 다음 액티비티로
                    uploadDataToFirebase();
                })
                .setNegativeButton("검토하기", (dialog, id) -> dialog.dismiss());

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void uploadDataToFirebase() {
        String title = titleEditText.getText().toString().trim();
        String itemType = changeButton.getText().toString().trim();
        String expirationDate = expirationDateEditText.getText().toString().trim();
        String details = detailsEditText.getText().toString().trim();
        String preferredCoupon = preferredCouponText.getText().toString().trim();

        HashMap<String, Object> giftMap = new HashMap<>();
        giftMap.put("title", title);
        giftMap.put("itemType", itemType);
        giftMap.put("expirationDate", expirationDate);
        giftMap.put("details", details);
        giftMap.put("preferredCoupon", preferredCoupon);

        // 경로 변경: giftcard/gifts 하위에 데이터 저장
        mDatabase.child("giftcard").child("gifts").push().setValue(giftMap)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ItemRegist_Gift.this, "등록 성공!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ItemRegist_Gift.this, ItemRegist_Finish.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ItemRegist_Gift.this, "등록 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}