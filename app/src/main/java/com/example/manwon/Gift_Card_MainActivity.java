package com.example.manwon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Gift_Card_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card_main);  // 위에 제공된 XML 레이아웃 파일

        configureCategoryButtons();

        // BottomNavigationView 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.home_icon){
                    transferTo(HomeFragment.newInstance("param1","param2"));
                    return true;
                }
                if(itemId == R.id.giftcard_icon){

                }
                if(itemId == R.id.feed_icon){

                }
                if(itemId == R.id.mypage_icon){

                }
                return false;
            }
        });
        // 이미 선택되어있는데 또 누른 경우 fragment가 다시 초기화 되지 않고 아무일도 안하도록 하기 위함
        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });

        // 처음 뜰 때는 Home이 뜨도록 함
        transferTo(HomeFragment.newInstance("param1", "param2"));
    }

    // 동적으로 Fragment를 교체하기 위해 사용
    // 즉, 해당 액티비티에서 특정 버튼을 클릭하면 다른 Fragment를 표시하도록 할 때 이 메서드를 사용.
    private void transferTo(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

    }

    private void configureCategoryButtons() {

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
        btnconvenience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 편의점마트 화면으로 이동
                Intent intent = new Intent(Gift_Card_MainActivity.this, ConvenienceActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnbread = findViewById(R.id.btn_bread);
        btnbread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 베이커리 화면으로 이동
                Intent intent = new Intent(Gift_Card_MainActivity.this, BreadActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnvoucher = findViewById(R.id.btn_voucher);
        btnvoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 상품권 화면으로 이동
                Intent intent = new Intent(Gift_Card_MainActivity.this, VoucherActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnicecream = findViewById(R.id.btn_icecream);
        btnicecream.setOnClickListener(new View.OnClickListener() {
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
