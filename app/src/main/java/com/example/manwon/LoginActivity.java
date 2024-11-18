package com.example.manwon;


import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manwon.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    
    private boolean isPasswordVisible = false;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 비밀번호 보기/숨기기 버튼 클릭 리스너
        binding.showPassword.setOnClickListener(v -> togglePasswordVisibility());

        // 로그인 버튼 클릭 리스너
        binding.loginButton.setOnClickListener(v -> {
            String username = binding.username.getText().toString();
            String password = binding.password.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "빈 칸 없이 입력하세요.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "로그인 시도 (테스트 메시지)", Toast.LENGTH_SHORT).show();
            }
        });

//        // 비밀번호 찾기 버튼 클릭 리스너
//        binding.forgotPasswordBtn.setOnClickListener(v -> {
//            Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
//            startActivity(intent);
//        });

        // 회원가입 버튼 클릭 리스너
        binding.registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    // 비밀번호 보기/숨기기 기능
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // 현재 보이는 상태 -> 비밀번호 숨기기
            binding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.showPassword.setImageResource(R.drawable.eye_off); // 눈 감김 아이콘
        } else {
            // 현재 숨겨진 상태 -> 비밀번호 보이기
            binding.password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            binding.showPassword.setImageResource(R.drawable.eye); // 눈 뜸 아이콘
        }
        isPasswordVisible = !isPasswordVisible;

        // 커서를 맨 끝으로 이동
        binding.password.setSelection(binding.password.getText().length());
    }
}
