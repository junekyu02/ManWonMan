package com.example.manwon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

// 스플래시 화면 Activity

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Firebase 초기화
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_splash);

        // 전체화면 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 상태 바와 내비게이션 바를 숨깁니다.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY; // 더 강력한 임머시브 모드
            decorView.setSystemUiVisibility(uiOptions);

            // 배경색을 핑크색으로 설정
            decorView.setBackgroundColor(Color.parseColor("#FFD8D8"));  // 핑크색
        }

        // 텍스트 3줄에 대한 세부 설정을 코드로 작업하기 위함
        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);

        // 기존 텍스트 가져오기
        String Text1 = textView1.getText().toString();
        String Text2 = textView2.getText().toString();
        String Text3 = textView3.getText().toString();

        // SpannableString으로 텍스트 감싸기
        SpannableString spannableString1 = new SpannableString(Text1);
        SpannableString spannableString2 = new SpannableString(Text2);
        SpannableString spannableString3 = new SpannableString(Text3);

        // 첫번째 글자만 초록색 및 굵게 설정
        spannableString1.setSpan(new ForegroundColorSpan(0xFF4CAF50), 0, 1, 0);
        spannableString1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 1, 0);
        spannableString2.setSpan(new ForegroundColorSpan(0xFF4CAF50), 0, 1, 0);
        spannableString2.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 1, 0);
        spannableString3.setSpan(new ForegroundColorSpan(0xFF4CAF50), 0, 1, 0);
        spannableString3.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 1, 0);

        // 텍스트 뷰에 설정
        textView1.setText(spannableString1);
        textView2.setText(spannableString2);
        textView3.setText(spannableString3);


        // 애니메이션 파일 로드
        Animation fadeIn1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);  // fade_in.xml 로드
        Animation fadeIn2 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeIn3 = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // 초기 상태에서 텍스트를 보이지 않도록 설정
        textView1.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        textView3.setVisibility(View.INVISIBLE);

        // 애니메이션을 1초 후, 2초 후, 3초 후에 각각 실행
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView1.setVisibility(View.VISIBLE);
                textView1.startAnimation(fadeIn1);
            }
        }, 0); // 0초 후에 첫 번째 텍스트 애니메이션 시작

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView2.setVisibility(View.VISIBLE);
                textView2.startAnimation(fadeIn2);
            }
        }, 1000); // 1초 후에 두 번째 텍스트 애니메이션 시작

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView3.setVisibility(View.VISIBLE);
                textView3.startAnimation(fadeIn3);
            }
        }, 2000); // 2초 후에 세 번째 텍스트 애니메이션 시작

        // 3초 후 LoginActivity로 이동
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // LoginActivity로 이동
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // SplashActivity 종료 (뒤로 가기 버튼 눌러도 돌아가지 않도록)
            }
        }, 3000); // 3초 후에 실행
    }
}