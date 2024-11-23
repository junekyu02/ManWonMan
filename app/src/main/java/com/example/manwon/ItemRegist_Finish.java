package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class ItemRegist_Finish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_regist_finish);

        // 5초 후에 BottomNavigation_Main 액티비티로 이동
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // BottomNavigation_Main 액티비티로 이동
                Intent intent = new Intent(ItemRegist_Finish.this, BottomNavigation_Main.class);
                startActivity(intent);
                finish();    // 현재 액티비티 finish 하기
            }
        }, 5000);
    }
}