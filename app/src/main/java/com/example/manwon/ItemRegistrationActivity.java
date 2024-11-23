package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

// 물품 등록 화면
public class ItemRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_registration);

        // ImageButton을 코드에서 참조
        ImageButton imageButton_left = findViewById(R.id.imagebutton_left);
        ImageButton imageButton_right = findViewById(R.id.imagebutton_right);

        // 왼쪽 버튼 클릭 이벤트 리스너 설정
        imageButton_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 시 ItemRegist_exchange 액티비티로 이동
                Intent intent = new Intent(ItemRegistrationActivity.this, ItemRegist_gonggu.class);
                startActivity(intent);
            }
        });

        // 오른쪽 버튼 클릭 이벤트 리스너 설정
        imageButton_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 시 해당 액티비티로 이동
                Intent intent = new Intent(ItemRegistrationActivity.this, ItemRegist_exchange.class);
                startActivity(intent);
            }
        });
    }
}