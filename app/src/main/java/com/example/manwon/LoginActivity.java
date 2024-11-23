package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.manwon.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private boolean isPasswordVisible = false;
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth; // FirebaseAuth 객체 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // FirebaseAuth 초기화
        mAuth = FirebaseAuth.getInstance();

        // 비밀번호 보기/숨기기 버튼 클릭 리스너
        binding.showPassword.setOnClickListener(v -> togglePasswordVisibility());

        // 로그인 버튼 클릭 리스너
        binding.loginButton.setOnClickListener(v -> {
            String email = binding.username.getText().toString().trim(); // 이메일 입력 값
            String password = binding.password.getText().toString().trim(); // 비밀번호 입력 값

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "빈 칸 없이 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Firebase 로그인 시도
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "로그인 성공: " + user.getEmail(), Toast.LENGTH_SHORT).show();

                            // 다음 액티비티로 이동 (MapViewActivity로 이동)
                            Intent intent = new Intent(LoginActivity.this, MapViewActivity.class);
                            startActivity(intent);
                            finish(); // 로그인 화면 종료
                        } else {
                            // 로그인 실패
                            Toast.makeText(LoginActivity.this, "로그인 실패: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

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
