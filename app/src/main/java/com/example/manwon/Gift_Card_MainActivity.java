package com.example.manwon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;

public class Gift_Card_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card_main);  // 위에 제공된 XML 레이아웃 파일

        // 각 버튼을 클릭했을 때 카테고리 화면으로 이동
        ImageButton btnCafeDessert = findViewById(R.id.btn_cafe_dessert);
        btnCafeDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 카페 & 디저트 카테고리 화면으로 이동
                Intent intent = new Intent(Gift_Card_MainActivity.this, CafeDessertActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnChicken = findViewById(R.id.btn_chicken);
        btnChicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 치킨 카테고리 화면으로 이동
                Intent intent = new Intent(Gift_Card_MainActivity.this, ChickenActivity.class);
                startActivity(intent);
            }
        });

        // 다른 버튼들에 대해서도 동일한 방식으로 추가
        ImageButton btnFastFood = findViewById(R.id.btn_fast_food);
        btnFastFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 패스트푸드 카테고리 화면으로 이동
                Intent intent = new Intent(Gift_Card_MainActivity.this, FastFoodActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btntravel = findViewById(R.id.btn_travel);
        btntravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 여행숙박 카테고리 화면으로 이동
                Intent intent = new Intent(Gift_Card_MainActivity.this, TravelActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnconvenience = findViewById(R.id.btn_convenience);
        btnFastFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 편의점마트 화면으로 이동
                Intent intent = new Intent(Gift_Card_MainActivity.this, ConvenienceActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnbread = findViewById(R.id.btn_bread);
        btnFastFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 베이커리 화면으로 이동
                Intent intent = new Intent(Gift_Card_MainActivity.this, BreadActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnvoucher = findViewById(R.id.btn_voucher);
        btnFastFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 상품권 화면으로 이동
                Intent intent = new Intent(Gift_Card_MainActivity.this, VoucherActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnicecream = findViewById(R.id.btn_icecream);
        btnFastFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아이스크림 화면으로 이동
                Intent intent = new Intent(Gift_Card_MainActivity.this, Ice_CreamActivity.class);
                startActivity(intent);
            }
        });
        // 나머지 버튼들에 대해서도 위와 같은 방식으로 작성
    }
}
