//package com.example.manwon;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Patterns;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class ItemRegist_exchange extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_item_regist_exchange);
//
//        ImageButton backButton = findViewById(R.id.back_button);
//        backButton.setOnClickListener(v -> {
//            // 현재 액티비티 종료하고 이전 화면으로 돌아감
//            finish();
//        });
//
//        EditText title = findViewById(R.id.title);
//        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 포커스를 받으면 텍스트를 지운다.
//                    title.setHint("");
//                } else {
//                    // 포커스를 잃으면 텍스트가 비어있으면 "작성하기"로 돌아간다.
//                    if (title.getText().toString().isEmpty()) {
//                        title.setHint("작성하기");
//                    }
//                }
//            }
//        });
//
//        // itemtype1 클릭 시 Regist_Item_Category로 이동
//        TextView itemtype1 = findViewById(R.id.itemtype1);
//        itemtype1.setOnClickListener(v -> {
//            Intent intent = new Intent(ItemRegist_exchange.this, Regist_Item_Category.class);
//            intent.putExtra("itemtype", "itemtype1");  // 어떤 TextView를 클릭했는지 구분
//            startActivityForResult(intent, 200);  // 요청 코드 200으로 결과를 받음
//        });
//
//        // itemtype2 클릭 시 Regist_Item_Category로 이동
//        TextView itemtype2 = findViewById(R.id.itemtype2);
//        itemtype2.setOnClickListener(v -> {
//            Intent intent = new Intent(ItemRegist_exchange.this, Regist_Item_Category.class);
//            intent.putExtra("itemtype", "itemtype2");  // 어떤 TextView를 클릭했는지 구분
//            startActivityForResult(intent, 201);  // 요청 코드 201으로 결과를 받음
//        });
//
//        EditText purchase_date = findViewById(R.id.purchase_date);
//        purchase_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 포커스를 받으면 텍스트를 지운다.
//                    purchase_date.setHint("");
//                } else {
//                    // 포커스를 잃으면 텍스트가 비어있으면 "클릭 시 카테고리 유형으로 이동합니다"로 돌아간다.
//                    if (purchase_date.getText().toString().isEmpty()) {
//                        purchase_date.setHint("클릭 시 카테고리 유형으로 이동합니다");
//                    }
//                }
//            }
//        });
//
//        EditText damage_rate = findViewById(R.id.damage_rate);
//        damage_rate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 포커스를 받으면 텍스트를 지운다.
//                    damage_rate.setHint("");
//                } else {
//                    // 포커스를 잃으면 텍스트가 비어있으면 "작성하기"로 돌아간다.
//                    if (damage_rate.getText().toString().isEmpty()) {
//                        damage_rate.setHint("작성하기");
//                    }
//                }
//            }
//        });
//
//        EditText etc = findViewById(R.id.etc);
//        etc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 포커스를 받으면 텍스트를 지운다.
//                    etc.setHint("");
//                } else {
//                    // 포커스를 잃으면 텍스트가 비어있으면 "이미지에 대한 URL 작성하기"로 돌아간다.
//                    if (etc.getText().toString().isEmpty()) {
//                        etc.setHint("이미지에 대한 URL 작성하기");
//                    }
//                }
//            }
//        });
//
//        EditText url = findViewById(R.id.url);
//        url.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 포커스를 받으면 텍스트를 지운다.
//                    url.setHint("");
//                } else {
//                    // 포커스를 잃으면 텍스트가 비어있으면 "이미지에 대한 URL 작성하기"로 돌아간다.
//                    if (url.getText().toString().isEmpty()) {
//                        url.setHint("이미지에 대한 URL 작성하기");
//                    }
//                }
//            }
//        });
//
//        LinearLayout layout = findViewById(R.id.linearlayout); // 레이아웃 ID 확인
//        if (layout != null) {  // layout이 null인 경우를 방지
//            layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // 현재 포커스를 가진 뷰를 가져옴
//                    View focusedView = getCurrentFocus();
//
//                    // focusedView가 EditText인 경우
//                    if (focusedView instanceof EditText) {
//                        EditText editText = (EditText) focusedView;
//
//                        // EditText가 아닌 곳을 터치했을 때
//                        if (v != focusedView) {
//                            // 포커스를 제거하여 커서를 숨기고 힌트를 설정
//                            editText.clearFocus();
//
//                            // 힌트 설정 (입력된 텍스트가 없을 때만)
//                            if (editText.getText().toString().isEmpty()) {
//                                String resourceName = getResources().getResourceName(editText.getId()); // 리소스 이름을 문자열로 가져옴
//                                if (resourceName != null) {
//                                    if (resourceName.endsWith("title") || resourceName.endsWith("purchase_date") || resourceName.endsWith("etc")) {
//                                        editText.setHint("작성하기");
//                                    } else if (resourceName.endsWith("itemtype1") || resourceName.endsWith("itemtype2")) {
//                                        editText.setHint("클릭 시 카테고리 유형으로 이동합니다");
//                                    } else if (resourceName.endsWith("damage_rate")) {
//                                        editText.setHint("파손 정도에 따라 '80% 손상', '50% 손상', '파손 없음' 중 한가지로 작성하기");
//                                    } else if (resourceName.endsWith("url")) {
//                                        editText.setHint("이미지에 대한 URL 작성하기");
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            });
//        }
//
//
//        TextView urlValidationMessage = findViewById(R.id.urlValidationMessage);
//
//        // URL 검증을 위한 TextWatcher 적용
//        url.addTextChangedListener(createUrlTextWatcher(urlValidationMessage));
//    }
//
//    private TextWatcher createUrlTextWatcher(TextView urlValidationMessage) {
//        return new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // URL 형식 검사
//                if (!Patterns.WEB_URL.matcher(s.toString()).matches()) {
//                    // URL 형식이 잘못되었을 경우
//                    urlValidationMessage.setText("Invalid URL Format");
//                    urlValidationMessage.setTextColor(Color.RED);  // 빨간색 메시지
//                    urlValidationMessage.setVisibility(View.VISIBLE);  // 메시지를 보이게 함
//                } else {
//                    // URL 형식이 맞을 경우
//                    urlValidationMessage.setText("Valid URL Format");
//                    urlValidationMessage.setTextColor(Color.GREEN);  // 초록색 메시지
//                    urlValidationMessage.setVisibility(View.VISIBLE);  // 메시지를 보이게 함
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//        };
//    }
//
//    // onActivityResult() 메서드 오버라이드 (2번 액티비티로부터 결과 받기)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK && data != null) {
//            String resultText = data.getStringExtra("selectedText");  // 2번 액티비티에서 전달된 텍스트
//            int selectedColor = data.getIntExtra("selectedColor", Color.BLACK);
//
//            if (requestCode == 200) {
//                // itemtype1 TextView 텍스트 변경
//                TextView itemtype1 = findViewById(R.id.itemtype1);
//                itemtype1.setText(resultText);
//                itemtype1.setTextColor(selectedColor);
//            } else if (requestCode == 201) {
//                // itemtype2 TextView 텍스트 변경
//                TextView itemtype2 = findViewById(R.id.itemtype2);
//                itemtype2.setText(resultText);
//                itemtype2.setTextColor(selectedColor);
//            }
//        }
//    }
//
//    // Submit 버튼 클릭 시 호출되는 메서드
//    public void onSubmitButtonClick(View view) {
//        // AlertDialog 생성
//        AlertDialog.Builder builder = new AlertDialog.Builder(ItemRegist_exchange.this);
//
//        builder.setIcon(R.drawable.registration_warning); // 다이얼로그 아이콘 설정
//        builder.setTitle("정말 등록하시겠습니까?");
//
//        builder.setMessage("빠진 부분이 있는지 반드시 확인해주세요.")  // 다이얼로그에 표시할 메시지
//                .setCancelable(false)  // 다이얼로그 외부 터치 시 닫히지 않도록 설정
//                .setPositiveButton("제출하기", (dialog, id) -> {
//                    // 제출하기 버튼 클릭 시 다음 액티비티로 이동
//                    Intent intent = new Intent(ItemRegist_exchange.this, ItemRegist_Finish.class);
//                    startActivity(intent);  // 액티비티 전환
//                    ItemRegist_exchange.this.finish();    // submit 버튼 클릭 시에 ItemRegist_Finish 액티비티로 넘어가면서 교환하기 등록 화면은 제거함
//                })
//                .setNegativeButton("검토하기", (dialog, id) -> {
//                    // 취소 버튼 클릭 시 다이얼로그 닫기
//                    dialog.dismiss();
//                });
//
//        // 다이얼로그 띄우기
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
//}


package com.example.manwon;

import android.content.Intent;
import android.graphics.Color;
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

public class ItemRegist_exchange extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_regist_exchange);

        // Firebase 초기화
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // UI 요소 연결
        EditText title = findViewById(R.id.title);
        EditText purchaseDate = findViewById(R.id.purchase_date);
        EditText damageRate = findViewById(R.id.damage_rate);
        EditText etc = findViewById(R.id.etc);
        EditText url = findViewById(R.id.url);
        TextView itemtype1 = findViewById(R.id.itemtype1);
        TextView itemtype2 = findViewById(R.id.itemtype2);
        TextView urlValidationMessage = findViewById(R.id.urlValidationMessage);

        // 뒤로가기 버튼
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // Focus 처리
        setFocusHandlers(title, "작성하기");
        setFocusHandlers(purchaseDate, "작성하기");
        setFocusHandlers(damageRate, "파손 정도에 따라 '80% 손상', '50% 손상', '파손 없음' 중 한 가지로 작성하기");
        setFocusHandlers(etc, "작성하기");
        setFocusHandlers(url, "이미지에 대한 URL 작성하기");

        // URL 검증
        url.addTextChangedListener(createUrlTextWatcher(urlValidationMessage));

        // 카테고리 유형 선택 이동
        itemtype1.setOnClickListener(v -> openCategorySelection("itemtype1", 200));
        itemtype2.setOnClickListener(v -> openCategorySelection("itemtype2", 201));

        // 레이아웃 클릭 시 포커스 해제
        LinearLayout layout = findViewById(R.id.linearlayout);
        if (layout != null) {
            layout.setOnClickListener(v -> clearFocusFromEditText());
        }

        // 제출 버튼 클릭 이벤트
        findViewById(R.id.submit_button_exchange).setOnClickListener(v -> {
            // 데이터 가져오기
            String titleStr = title.getText().toString().trim();
            String purchaseDateStr = purchaseDate.getText().toString().trim();
            String damageRateStr = damageRate.getText().toString().trim();
            String etcStr = etc.getText().toString().trim();
            String urlStr = url.getText().toString().trim();
            String itemtype1Str = itemtype1.getText().toString().trim();
            String itemtype2Str = itemtype2.getText().toString().trim();

            // 기본값 처리
            if (titleStr.isEmpty()) titleStr = "제목 없음";
            if (purchaseDateStr.isEmpty()) purchaseDateStr = "구매일자 미기입";
            if (damageRateStr.isEmpty()) damageRateStr = "손상도 미기입";
            if (etcStr.isEmpty()) etcStr = "특이사항 없음";

            // 사용자 지역 정보를 가져오기
            DatabaseReference locationRef = databaseReference.child("location").child(userId);
            String finalTitleStr = titleStr;
            String finalPurchaseDateStr = purchaseDateStr;
            String finalDamageRateStr = damageRateStr;
            String finalEtcStr = etcStr;
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

                    // 기본 지역 설정
                    if (userRegions.isEmpty()) {
                        userRegions.add("기타지역");
                    }

                    // Firebase에 지역별 데이터 저장
                    for (String region : userRegions) {
                        String regionKey = region.replace(" ", "_");
                        DatabaseReference ref = databaseReference.child("exchangeItems").child(regionKey).child(userId);

                        // 데이터 모델 생성
                        ExchangeItem_Model newItem = new ExchangeItem_Model(
                                region,  // 지역 정보
                                itemtype1Str,
                                itemtype2Str,
                                finalTitleStr,
                                finalPurchaseDateStr,
                                finalDamageRateStr,
                                finalEtcStr,
                                urlStr,
                                userId,
                                false // 찜 상태 기본값
                        );

                        // Firebase에 저장
                        ref.push().setValue(newItem).addOnSuccessListener(aVoid -> {
                            // 성공 메시지 (필요 시 추가 작업 가능)
                        }).addOnFailureListener(e -> {
                            // 실패 메시지
                            AlertDialog.Builder builder = new AlertDialog.Builder(ItemRegist_exchange.this);
                            builder.setTitle("저장 실패")
                                    .setMessage("데이터 저장 중 문제가 발생했습니다.")
                                    .setPositiveButton("확인", null)
                                    .show();
                        });
                    }

                    // 등록 완료 화면으로 이동
                    Intent intent = new Intent(ItemRegist_exchange.this, ItemRegist_Finish.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ItemRegist_exchange.this);
                    builder.setTitle("등록 실패")
                            .setMessage("지역 정보를 불러오지 못했습니다.")
                            .setPositiveButton("확인", null)
                            .show();
                }
            });
        });
    }

    // Focus 처리 함수
    private void setFocusHandlers(EditText editText, String defaultHint) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                editText.setHint("");
            } else if (editText.getText().toString().isEmpty()) {
                editText.setHint(defaultHint);
            }
        });
    }

    // URL 검증 함수
    private TextWatcher createUrlTextWatcher(TextView urlValidationMessage) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    // 카테고리 선택 화면 열기
    private void openCategorySelection(String itemType, int requestCode) {
        Intent intent = new Intent(ItemRegist_exchange.this, Regist_Item_Category.class);
        intent.putExtra("itemtype", itemType);
        startActivityForResult(intent, requestCode);
    }

    // 포커스 해제
    private void clearFocusFromEditText() {
        View focusedView = getCurrentFocus();
        if (focusedView instanceof EditText) {
            EditText editText = (EditText) focusedView;
            editText.clearFocus();
        }
    }

    // onActivityResult()로 카테고리 데이터 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String resultText = data.getStringExtra("selectedText");
            int selectedColor = data.getIntExtra("selectedColor", Color.BLACK);

            if (requestCode == 200) {
                TextView itemtype1 = findViewById(R.id.itemtype1);
                itemtype1.setText(resultText);
                itemtype1.setTextColor(selectedColor);
            } else if (requestCode == 201) {
                TextView itemtype2 = findViewById(R.id.itemtype2);
                itemtype2.setText(resultText);
                itemtype2.setTextColor(selectedColor);
            }
        }
    }
}
