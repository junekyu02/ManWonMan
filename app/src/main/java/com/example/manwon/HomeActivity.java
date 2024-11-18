package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // button2에 클릭 리스너 설정
        ImageButton button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ItemRegistrationActivity로 이동
                Intent intent = new Intent(HomeActivity.this, ItemRegistrationActivity.class);
                startActivity(intent);  // 액티비티 시작
            }
        });
    }
}