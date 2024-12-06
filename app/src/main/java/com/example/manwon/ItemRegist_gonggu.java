package com.example.manwon;

import android.content.Intent;
import android.graphics.Color;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemRegist_gonggu extends AppCompatActivity {

    private SoundPool soundPool;
    private int clickSoundId;

    private EditText title, content, total_number, url;
    private TextView itemtype1;
    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_regist_gonggu);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        title = findViewById(R.id.title);
        title.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                title.setHint("");
            } else {
                if (title.getText().toString().isEmpty()) {
                    title.setHint("작성하기");
                }
            }
        });

        itemtype1 = findViewById(R.id.itemtype1);
        itemtype1.setOnClickListener(v -> {
            Intent intent = new Intent(ItemRegist_gonggu.this, Regist_Item_Category.class);
            intent.putExtra("itemtype", "itemtype1");
            startActivityForResult(intent, 100);
        });

        content = findViewById(R.id.content);
        content.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                content.setHint("");
            } else {
                if (content.getText().toString().isEmpty()) {
                    content.setHint("작성하기");
                }
            }
        });

        soundPool = new SoundPool.Builder().setMaxStreams(1).build();
        clickSoundId = soundPool.load(this, R.raw.button_click, 1);

        ImageButton coupangWebsite = findViewById(R.id.coupang_icon);
        ImageButton gmarketWebsite = findViewById(R.id.gmarket_icon);
        ImageButton ssgWebsite = findViewById(R.id.ssg_icon);
        ImageButton auctionWebsite = findViewById(R.id.auction_icon);

        coupangWebsite.setOnClickListener(view -> {
            soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);
            String urlStr = "https://www.coupang.com/np/search?q=%EC%98%A4%EB%8A%98%EC%9D%98%ED%8A%B9%EA%B0%80%ED%95%A0%EC%9D%B8&channel=relate";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr));
            startActivity(intent);
        });

        gmarketWebsite.setOnClickListener(view -> {
            soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);
            String urlStr = "https://www.gmarket.co.kr/n/search?keyword=%ED%8A%B9%EA%B0%80";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr));
            startActivity(intent);
        });

        ssgWebsite.setOnClickListener(view -> {
            soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);
            String urlStr = "https://www.ssg.com/page/pc/SpecialPrice.ssg";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr));
            startActivity(intent);
        });

        auctionWebsite.setOnClickListener(view -> {
            soundPool.play(clickSoundId, 1f, 1f, 0, 0, 1f);
            String urlStr = "https://www.auction.co.kr/n/search?keyword=%EC%B4%88%ED%8A%B9%EA%B0%80%EC%84%B8%EC%9D%BC";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr));
            startActivity(intent);
        });

        total_number = findViewById(R.id.total_number);
        total_number.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                total_number.setHint("");
            } else {
                if (total_number.getText().toString().isEmpty()) {
                    total_number.setHint("작성하기");
                }
            }
        });

        url = findViewById(R.id.url);
        url.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                url.setHint("");
            } else {
                if (url.getText().toString().isEmpty()) {
                    url.setHint("이미지에 대한 URL 작성하기");
                }
            }
        });

        LinearLayout layout = findViewById(R.id.linearlayout);
        if (layout != null) {
            layout.setOnClickListener(v -> {
                View focusedView = getCurrentFocus();
                if (focusedView instanceof EditText && v != focusedView) {
                    EditText editText = (EditText) focusedView;
                    editText.clearFocus();
                    if (editText.getText().toString().isEmpty()) {
                        String resourceName = getResources().getResourceName(editText.getId());
                        if (resourceName != null) {
                            if (resourceName.endsWith("title") || resourceName.endsWith("content") || resourceName.endsWith("total_number")) {
                                editText.setHint("작성하기");
                            } else if (resourceName.endsWith("url")) {
                                editText.setHint("이미지에 대한 URL 작성하기");
                            }
                        }
                    }
                }
            });
        }

        TextView urlValidationMessage = findViewById(R.id.urlValidationMessage);
        url.addTextChangedListener(createUrlTextWatcher(urlValidationMessage));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String resultText = data.getStringExtra("selectedText");
            int selectedColor = data.getIntExtra("selectedColor", Color.BLACK);

            if (requestCode == 100) {
                TextView itemtype1 = findViewById(R.id.itemtype1);
                itemtype1.setText(resultText);
                itemtype1.setTextColor(selectedColor);
            }
        }
    }

    // Submit 버튼 클릭 시 호출되는 메서드
    public void onSubmitButtonClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemRegist_gonggu.this);
        builder.setIcon(R.drawable.registration_warning);
        builder.setTitle("정말 등록하시겠습니까?");
        builder.setMessage("빠진 부분이 있는지 반드시 확인해주세요.")
                .setCancelable(false)
                .setPositiveButton("제출하기", (dialog, id) -> {
                    // 아이템 정보 가져오기
                    String titleStr = title.getText().toString().trim();
                    String categoryStr = ((TextView)findViewById(R.id.itemtype1)).getText().toString().trim();
                    String contentStr = content.getText().toString().trim();
                    String imgUrlStr = url.getText().toString().trim();
                    String totalNumStr = total_number.getText().toString().trim();

                    int targetParticipants = 30;
                    if (!totalNumStr.isEmpty()) {
                        try {
                            targetParticipants = Integer.parseInt(totalNumStr);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }

                    GongguItem_Model newItem = new GongguItem_Model(
                            categoryStr.isEmpty()? "기타" : categoryStr,
                            titleStr.isEmpty()? "제목없음" : titleStr,
                            contentStr.isEmpty()? "상세 내용 없음" : contentStr,
                            imgUrlStr,
                            false,
                            0,
                            targetParticipants
                    );

                    // 현재 사용자 지역정보 가져와서 해당 지역에 저장
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
                                // 지역이 없는 경우 기본값 처리
                                userRegions.add("기타지역");
                            }

                            // 각 지역에 아이템 저장
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

                })
                .setNegativeButton("검토하기", (dialog, id) -> dialog.dismiss());

        AlertDialog alert = builder.create();
        alert.show();
    }
}
