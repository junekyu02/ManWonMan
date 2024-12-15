package com.example.manwon;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemRegist_gonggu extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1; // 이미지 선택 요청 코드
    private ImageView selectedImageView; // 선택된 이미지 미리보기
    private ImageButton cameraButton; // 이미지 선택 버튼
    private Uri selectedImageUri; // 선택된 이미지의 URI 저장

    private EditText title, content, total_number, url;
    private TextView itemtype1;
    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_regist_gonggu);

        // Firebase 설정
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // UI 초기화
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        total_number = findViewById(R.id.total_number);
        url = findViewById(R.id.url);
        itemtype1 = findViewById(R.id.itemtype1);
        selectedImageView = findViewById(R.id.selected_image);
        cameraButton = findViewById(R.id.camera_imagebutton);

        // 뒤로가기 버튼 설정
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // 제목 입력 힌트 처리
        title.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) title.setHint("");
            else if (title.getText().toString().isEmpty()) title.setHint("작성하기");
        });

        // 카테고리 선택
        itemtype1.setOnClickListener(v -> {
            Intent intent = new Intent(ItemRegist_gonggu.this, Regist_Item_Category.class);
            intent.putExtra("itemtype", "itemtype1");
            startActivityForResult(intent, 100);
        });

        // 상세 내용 힌트 처리
        content.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) content.setHint("");
            else if (content.getText().toString().isEmpty()) content.setHint("작성하기");
        });

        // URL 입력 유효성 검사
        TextView urlValidationMessage = findViewById(R.id.urlValidationMessage);
        url.addTextChangedListener(createUrlTextWatcher(urlValidationMessage));

        // 목표 인원수 입력 힌트 처리
        total_number.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) total_number.setHint("");
            else if (total_number.getText().toString().isEmpty()) total_number.setHint("작성하기");
        });

        // 이미지 선택 버튼 클릭 리스너
        cameraButton.setOnClickListener(v -> openGallery());

        // 레이아웃 클릭 시 입력 포커스 해제 처리
        LinearLayout layout = findViewById(R.id.linearlayout);
        if (layout != null) {
            layout.setOnClickListener(v -> {
                View focusedView = getCurrentFocus();
                if (focusedView instanceof EditText) {
                    EditText editText = (EditText) focusedView;
                    editText.clearFocus();
                }
            });
        }

        // 쇼핑몰 웹사이트 아이콘 클릭 리스너 설정
        setupShoppingLinks();
    }

    private void setupShoppingLinks() {
        ImageButton coupangWebsite = findViewById(R.id.coupang_icon);
        ImageButton gmarketWebsite = findViewById(R.id.gmarket_icon);
        ImageButton ssgWebsite = findViewById(R.id.ssg_icon);
        ImageButton auctionWebsite = findViewById(R.id.auction_icon);

        coupangWebsite.setOnClickListener(view -> openWebsite("https://www.coupang.com/np/search?q=%EC%98%A4%EB%8A%98%EC%9D%98%ED%8A%B9%EA%B0%80%ED%95%A0%EC%9D%B8&channel=relate"));
        gmarketWebsite.setOnClickListener(view -> openWebsite("https://www.gmarket.co.kr/n/search?keyword=%ED%8A%B9%EA%B0%80"));
        ssgWebsite.setOnClickListener(view -> openWebsite("https://www.ssg.com/page/pc/SpecialPrice.ssg"));
        auctionWebsite.setOnClickListener(view -> openWebsite("https://www.auction.co.kr/n/search?keyword=%EC%B4%88%ED%8A%B9%EA%B0%80%EC%84%B8%EC%9D%BC"));
    }

    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private TextWatcher createUrlTextWatcher(TextView urlValidationMessage) {
        return new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Patterns.WEB_URL.matcher(s.toString()).matches()) {
                    urlValidationMessage.setText("Invalid URL Format");
                    urlValidationMessage.setTextColor(Color.RED);
                    urlValidationMessage.setVisibility(View.VISIBLE);
                } else {
                    urlValidationMessage.setText("Valid URL Format");
                    urlValidationMessage.setTextColor(Color.GREEN);
                    urlValidationMessage.setVisibility(View.VISIBLE);
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        };
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            Glide.with(this).load(selectedImageUri).into(selectedImageView);
            selectedImageView.setVisibility(View.VISIBLE); // 이미지뷰 표시
            cameraButton.setVisibility(View.GONE); // 카메라 버튼 숨김
        } else if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            String resultText = data.getStringExtra("selectedText");
            int selectedColor = data.getIntExtra("selectedColor", Color.BLACK);
            itemtype1.setText(resultText);
            itemtype1.setTextColor(selectedColor);
        }
    }

    public void onSubmitButtonClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.registration_warning)
                .setTitle("정말 등록하시겠습니까?")
                .setMessage("빠진 부분이 있는지 반드시 확인해주세요.")
                .setPositiveButton("제출하기", (dialog, id) -> saveItem())
                .setNegativeButton("검토하기", (dialog, id) -> dialog.dismiss())
                .create()
                .show();
    }

    private void saveItem() {
        String titleStr = title.getText().toString().trim();
        String categoryStr = itemtype1.getText().toString().trim();
        String contentStr = content.getText().toString().trim();
        String totalNumStr = total_number.getText().toString().trim();
        String imgUrl = selectedImageUri != null ? selectedImageUri.toString() : url.getText().toString().trim();

        int targetParticipants = 30;
        if (!totalNumStr.isEmpty()) {
            try {
                targetParticipants = Integer.parseInt(totalNumStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        GongguItem_Model newItem = new GongguItem_Model(
                categoryStr.isEmpty() ? "기타" : categoryStr,
                titleStr.isEmpty() ? "제목없음" : titleStr,
                contentStr.isEmpty() ? "상세 내용 없음" : contentStr,
                imgUrl,
                false,
                0,
                targetParticipants,
                userId
        );

        DatabaseReference locationRef = FirebaseDatabase.getInstance().getReference("location").child(userId);
        locationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> userRegions = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String region = ds.getValue(String.class);
                    if (region != null && !region.isEmpty()) {
                        userRegions.add(region);
                    }
                }

                if (userRegions.isEmpty()) {
                    userRegions.add("기타지역");
                }

                for (String region : userRegions) {
                    String regionKey = region.replace(" ", "_");
                    databaseReference.child("gongguItems").child(regionKey).child(userId).push().setValue(newItem);
                }

                Intent intent = new Intent(ItemRegist_gonggu.this, ItemRegist_Finish.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
