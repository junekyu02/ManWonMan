package com.example.manwon;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class ItemRegist_Finish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_regist_finish);

        // 2초 후에 BottomNavigation_Main 액티비티로 이동
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 현재 ItemRegist_Finish 종료 -> 이전에 남아있는 액티비티는 BottomNavigation_Main뿐이므로 2초 후에 BottomNavigation_Main으로 돌아가게 됨
                finish();    // 현재 액티비티 finish 하기
            }
        }, 2000);
    }
}