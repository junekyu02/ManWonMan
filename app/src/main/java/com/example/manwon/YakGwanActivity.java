package com.example.manwon;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manwon.databinding.ActivityYakGwanBinding;

//import com.unit_3.sogong_test.databinding.ActivityTermsBinding;

public class YakGwanActivity extends AppCompatActivity {

    private ActivityYakGwanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View Binding 초기화
        binding = ActivityYakGwanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // '모두 동의' 체크박스 클릭 시 모든 체크박스 상태 변경
        binding.allCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.ageCheckbox.setChecked(isChecked);
            binding.termsCheckbox.setChecked(isChecked);
            binding.privacyCheckbox.setChecked(isChecked);
            binding.adsCheckbox.setChecked(isChecked);
        });

        // '동의' 버튼 클릭 이벤트
        binding.acceptTermsBtn.setOnClickListener(view -> {
            if (binding.ageCheckbox.isChecked() &&
                    binding.termsCheckbox.isChecked() &&
                    binding.privacyCheckbox.isChecked()) {
                Intent Intent = new Intent(YakGwanActivity.this, LoginActivity.class);
                Intent.putExtra("isAgreed", true);
                startActivity(Intent); // LoginActivity 실행
                finish(); // 현재 화면 종료 (선택)
            } else {
                Toast.makeText(this, "필수 항목에 동의해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
