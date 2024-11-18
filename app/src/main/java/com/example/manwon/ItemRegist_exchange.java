package com.example.manwon;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

public class ItemRegist_exchange extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_regist_exchange);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            // 현재 액티비티 종료하고 이전 화면으로 돌아감
            finish();
        });

        EditText title = findViewById(R.id.title);
        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 포커스를 받으면 텍스트를 지운다.
                    title.setHint("");
                } else {
                    // 포커스를 잃으면 텍스트가 비어있으면 "작성하기"로 돌아간다.
                    if (title.getText().toString().isEmpty()) {
                        title.setHint("작성하기");
                    }
                }
            }
        });

        EditText itemtype1 = findViewById(R.id.itemtype1);
        itemtype1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 포커스를 받으면 텍스트를 지운다.
                    itemtype1.setHint("");
                } else {
                    // 포커스를 잃으면 텍스트가 비어있으면 "클릭 시 카테고리 유형으로 이동합니다"로 돌아간다.
                    if (itemtype1.getText().toString().isEmpty()) {
                        itemtype1.setHint("클릭 시 카테고리 유형으로 이동합니다");
                    }
                }
            }
        });

        EditText purchase_date = findViewById(R.id.purchase_date);
        purchase_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 포커스를 받으면 텍스트를 지운다.
                    purchase_date.setHint("");
                } else {
                    // 포커스를 잃으면 텍스트가 비어있으면 "클릭 시 카테고리 유형으로 이동합니다"로 돌아간다.
                    if (purchase_date.getText().toString().isEmpty()) {
                        purchase_date.setHint("클릭 시 카테고리 유형으로 이동합니다");
                    }
                }
            }
        });

        EditText damage_rate = findViewById(R.id.damage_rate);
        damage_rate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 포커스를 받으면 텍스트를 지운다.
                    damage_rate.setHint("");
                } else {
                    // 포커스를 잃으면 텍스트가 비어있으면 "작성하기"로 돌아간다.
                    if (damage_rate.getText().toString().isEmpty()) {
                        damage_rate.setHint("작성하기");
                    }
                }
            }
        });

        EditText etc = findViewById(R.id.etc);
        etc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 포커스를 받으면 텍스트를 지운다.
                    etc.setHint("");
                } else {
                    // 포커스를 잃으면 텍스트가 비어있으면 "이미지에 대한 URL 작성하기"로 돌아간다.
                    if (etc.getText().toString().isEmpty()) {
                        etc.setHint("이미지에 대한 URL 작성하기");
                    }
                }
            }
        });

        EditText itemtype2 = findViewById(R.id.itemtype2);
        itemtype2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 포커스를 받으면 텍스트를 지운다.
                    itemtype2.setHint("");
                } else {
                    // 포커스를 잃으면 텍스트가 비어있으면 "이미지에 대한 URL 작성하기"로 돌아간다.
                    if (itemtype2.getText().toString().isEmpty()) {
                        itemtype2.setHint("이미지에 대한 URL 작성하기");
                    }
                }
            }
        });

        EditText url = findViewById(R.id.url);
        url.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 포커스를 받으면 텍스트를 지운다.
                    url.setHint("");
                } else {
                    // 포커스를 잃으면 텍스트가 비어있으면 "이미지에 대한 URL 작성하기"로 돌아간다.
                    if (url.getText().toString().isEmpty()) {
                        url.setHint("이미지에 대한 URL 작성하기");
                    }
                }
            }
        });

        LinearLayout layout = findViewById(R.id.linearlayout); // 레이아웃 ID 확인
        if (layout != null) {  // layout이 null인 경우를 방지
            layout.setOnTouchListener((v, event) -> {
                // 현재 포커스를 가진 뷰를 가져옴
                View focusedView = getCurrentFocus();
                if (focusedView != null && focusedView instanceof EditText) {
                    focusedView.clearFocus();  // 포커스를 제거하여 커서 숨기기
                    // 입력된 텍스트가 없으면 힌트를 다시 설정
                    EditText editText = (EditText) focusedView;
                    if (editText.getText().toString().isEmpty()) {
                        String resourceName = getResources().getResourceName(editText.getId()); // 리소스 이름을 문자열로 가져옴
                        if (resourceName.endsWith("title")) {
                            editText.setHint("작성하기");
                        } else if (resourceName.endsWith("itemtype1")) {
                            editText.setHint("클릭 시 카테고리 유형으로 이동합니다");
                        } else if (resourceName.endsWith("purchase_date")) {
                            editText.setHint("작성하기");
                        } else if (resourceName.endsWith("damage_rate")){
                            editText.setHint("파손 정도에 따라 '80% 손상', '50% 손상', '파손 없음' 중 한가지로 작성하기");
                        } else if (resourceName.endsWith("etc")) {
                            editText.setHint("작성하기");
                        }else if (resourceName.endsWith("itemtype2")) {
                            editText.setHint("클릭 시 카테고리 유형으로 이동합니다");
                        } else if (resourceName.endsWith("url")) {
                            editText.setHint("이미지에 대한 URL 작성하기");
                        }
                    }
                }
                return false;  // 이벤트를 다른 곳으로 전달
            });
        }


        TextView urlValidationMessage = findViewById(R.id.urlValidationMessage);

        // URL 검증을 위한 TextWatcher 적용
        url.addTextChangedListener(createUrlTextWatcher(urlValidationMessage));
    }

    private TextWatcher createUrlTextWatcher(TextView urlValidationMessage) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // URL 형식 검사
                if (!Patterns.WEB_URL.matcher(s.toString()).matches()) {
                    // URL 형식이 잘못되었을 경우
                    urlValidationMessage.setText("Invalid URL Format");
                    urlValidationMessage.setTextColor(Color.RED);  // 빨간색 메시지
                    urlValidationMessage.setVisibility(View.VISIBLE);  // 메시지를 보이게 함
                } else {
                    // URL 형식이 맞을 경우
                    urlValidationMessage.setText("Valid URL Format");
                    urlValidationMessage.setTextColor(Color.GREEN);  // 초록색 메시지
                    urlValidationMessage.setVisibility(View.VISIBLE);  // 메시지를 보이게 함
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }
}