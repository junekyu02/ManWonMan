package com.example.manwon;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class ItemRegist_Gift extends AppCompatActivity {

    private ImageButton backButton, uploadImageButton;
    private EditText titleEditText, expirationDateEditText, detailsEditText;
    private TextView saveTempText, preferredCouponText, changeButton;

    // Firebase Database 참조
    private DatabaseReference mDatabase;
    private String currentUserUid; // 현재 사용자 UID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_regist_gift);

        // Firebase Database 초기화
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // 현재 로그인한 사용자 UID 가져오기

        backButton = findViewById(R.id.back_button);
        uploadImageButton = findViewById(R.id.gift_upload_image);
        titleEditText = findViewById(R.id.gift_title);
        changeButton = findViewById(R.id.gift_itemtype1);
        expirationDateEditText = findViewById(R.id.gift_expiration_date);
        detailsEditText = findViewById(R.id.gift_details);
        preferredCouponText = findViewById(R.id.gift_preferred_coupon);
        saveTempText = findViewById(R.id.gift_back_up);

        // 초기 커서 비표시
        titleEditText.setCursorVisible(false);
        expirationDateEditText.setCursorVisible(false);
        detailsEditText.setCursorVisible(false);

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

        // EditText 포커스 변화에 따른 힌트/커서 처리
        titleEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                titleEditText.setHint("");
                titleEditText.setCursorVisible(true);
            } else {
                if (titleEditText.getText().toString().trim().isEmpty()) {
                    titleEditText.setHint("작성하기");
                }
                titleEditText.setCursorVisible(false);
            }
        });

        // 키보드가 뜨지 않도록 설정
        expirationDateEditText.setShowSoftInputOnFocus(false);

        // 날짜 선택하도록 표시
        expirationDateEditText.setOnClickListener(v -> {
            expirationDateEditText.setHint("");

            // 현재 날짜 값
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, yearSelected, monthSelected, daySelected) -> {
                String selectedDate = yearSelected + "년 " + (monthSelected + 1) + "월 " + daySelected + "일";
                expirationDateEditText.setText(selectedDate);
            }, year, month, day);

            datePickerDialog.setOnCancelListener(dialogInterface -> {
                if (expirationDateEditText.getText().toString().trim().isEmpty()) {
                    expirationDateEditText.setHint("터치하여 선택");
                }
            });

            datePickerDialog.show();
        });

        detailsEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                detailsEditText.setHint("");
                detailsEditText.setCursorVisible(true);
            } else {
                if (detailsEditText.getText().toString().trim().isEmpty()) {
                    detailsEditText.setHint("작성하기");
                }
                detailsEditText.setCursorVisible(false);
            }
        });

        // 레이아웃 영역 클릭 시 포커스 해제 및 힌트 복구 처리
        LinearLayout layout = findViewById(R.id.linearlayout);
        if (layout != null) {
            layout.setOnClickListener(v -> {
                View focusedView = getCurrentFocus();

                if (focusedView instanceof EditText) {
                    EditText editText = (EditText) focusedView;
                    if (v != focusedView) {
                        editText.clearFocus();
                        editText.setCursorVisible(false);

                        if (editText.getText().toString().isEmpty()) {
                            String resourceName = getResources().getResourceEntryName(editText.getId());
                            if (resourceName != null) {
                                if (resourceName.endsWith("title")
                                        || resourceName.endsWith("details")) {
                                    editText.setHint("작성하기");
                                } else if (resourceName.endsWith("itemtype1")
                                        || resourceName.endsWith("preferred_coupon")) {
                                    editText.setHint("클릭 시 카테고리 유형으로 이동합니다");
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    private void saveTempData() {
        String title = titleEditText.getText().toString();
        String expirationDate = expirationDateEditText.getText().toString();
        String details = detailsEditText.getText().toString();
        String preferredCoupon = preferredCouponText.getText().toString();
        String itemType = changeButton.getText().toString();

        Toast.makeText(this, "임시저장 완료\n제목: " + title, Toast.LENGTH_SHORT).show();
    }

    private void uploadImage() {
        Toast.makeText(this, "이미지 업로드 버튼 클릭됨", Toast.LENGTH_SHORT).show();
    }

    public void onSubmitButtonClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemRegist_Gift.this);
        builder.setIcon(R.drawable.registration_warning);
        builder.setTitle("정말 등록하시겠습니까?");
        builder.setMessage("빠진 부분이 있는지 반드시 확인해주세요.")
                .setCancelable(false)
                .setPositiveButton("제출하기", (dialog, id) -> {
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

        if (title.isEmpty() || itemType.isEmpty() || expirationDate.isEmpty() || details.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> giftMap = new HashMap<>();
        giftMap.put("title", title);
        giftMap.put("itemType", itemType);
        giftMap.put("expirationDate", expirationDate);
        giftMap.put("details", details);
        giftMap.put("preferredCoupon", preferredCoupon);
        giftMap.put("sellerUid", currentUserUid); // 등록자 UID 추가
        giftMap.put("timestamp", System.currentTimeMillis()); // 등록 시간 추가

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String resultText = data.getStringExtra("selectedText");
            int selectedColor = data.getIntExtra("selectedColor", Color.BLACK);

            if (requestCode == 200) {
                TextView itemtype1 = findViewById(R.id.gift_itemtype1);
                itemtype1.setText(resultText);
                itemtype1.setTextColor(selectedColor);
            } else if (requestCode == 201) {
                TextView itemtype2 = findViewById(R.id.gift_preferred_coupon);
                itemtype2.setText(resultText);
                itemtype2.setTextColor(selectedColor);
            }
        }
    }
}
