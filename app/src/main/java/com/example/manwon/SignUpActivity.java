package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manwon.databinding.ActivitySignupBinding;

public class SignUpActivity extends AppCompatActivity {

    private boolean isNicknameChecked = false;
    private static final int TERMS_REQUEST_CODE = 1;

    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 텍스트 변경 감지
        binding.nameText.addTextChangedListener(createTextWatcher(binding.nicknameLayout, binding.nicknameMessage));
        binding.emailText.addTextChangedListener(createTextWatcher(binding.emailLayout, binding.emailMessage));
        binding.passwordText.addTextChangedListener(createTextWatcher(binding.passwordLayout, null));
        binding.repeatPasswordText.addTextChangedListener(createTextWatcher(binding.repeatPasswordLayout, null));

        // 비밀번호 표시 버튼
        binding.showPassword.setOnClickListener(view -> togglePasswordVisibility(binding.passwordText, binding.showPassword));
        binding.showRepeatPassword.setOnClickListener(view -> togglePasswordVisibility(binding.repeatPasswordText, binding.showRepeatPassword));

        // 닉네임 중복 확인 버튼
        binding.checkNicknameBtn.setOnClickListener(view -> {
            String nickname = binding.nameText.getText().toString().trim();
            if (!nickname.isEmpty()) {
                // 닉네임 중복 여부를 확인하는 로직 (더미로 처리)
                boolean exists = false; // 중복 여부 확인 로직은 구현 생략
                if (exists) {
                    binding.nicknameMessage.setText("이미 사용 중인 닉네임입니다!");
                    binding.nicknameMessage.setTextColor(getColor(R.color.red));
                    isNicknameChecked = false;
                } else {
                    binding.nicknameMessage.setText("사용 가능한 닉네임입니다.");
                    binding.nicknameMessage.setTextColor(getColor(R.color.blue));
                    isNicknameChecked = true;
                }
            } else {
                binding.nicknameMessage.setText("닉네임을 입력해 주세요.");
                binding.nicknameMessage.setTextColor(getColor(R.color.black));
                isNicknameChecked = false;
            }
        });

        // 회원가입 버튼
        binding.registerBtn.setOnClickListener(view -> {
            String email = binding.emailText.getText().toString().trim();
            String password = binding.passwordText.getText().toString().trim();
            String repeatPassword = binding.repeatPasswordText.getText().toString().trim();
            String nickname = binding.nameText.getText().toString().trim();

            boolean isValid = validateInputs(email, password, repeatPassword, nickname);

            if (isValid && isNicknameChecked) {
                // 이메일 중복 확인 로직 (더미 처리)
                boolean emailExists = false; // 이메일 확인 로직은 생략
                if (emailExists) {
                    binding.emailMessage.setText("이미 사용 중인 이메일입니다.");
                    binding.emailMessage.setTextColor(getColor(R.color.red));
                } else {
                    Intent intent = new Intent(SignUpActivity.this, YakGwanActivity.class);
                    startActivityForResult(intent, TERMS_REQUEST_CODE);
                }
            }
        });
    }

    private boolean validateInputs(String email, String password, String repeatPassword, String nickname) {
        boolean isValid = true;

        if (nickname.isEmpty()) {
            binding.nicknameMessage.setText("올바른 이름을 입력해 주세요.");
            binding.nicknameMessage.setTextColor(getColor(R.color.red));
            isValid = false;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailMessage.setText("올바른 이메일 형식이 아닙니다.");
            binding.emailMessage.setTextColor(getColor(R.color.red));
            isValid = false;
        } else {
            binding.emailMessage.setText("올바른 이메일입니다.");
            binding.emailMessage.setTextColor(getColor(R.color.green));
        }

        if (!isValidPassword(password)) {
            binding.passwordLayout.setError("숫자+영문자+특수문자 조합으로 8자리 이상 입력해 주세요.");
            isValid = false;
        } else {
            binding.passwordLayout.setError(null);
        }

        if (!password.equals(repeatPassword)) {
            binding.repeatPasswordLayout.setError("비밀번호가 일치하지 않습니다.");
            isValid = false;
        } else {
            binding.repeatPasswordLayout.setError(null);
        }

        return isValid;
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = ".*[A-Za-z].*";
        String numberRegex = ".*[0-9].*";
        String specialCharRegex = ".*[@#$%^&*()!].*";

        return !password.isEmpty() && password.length() >= 8 &&
                password.matches(passwordRegex) && password.matches(numberRegex) && password.matches(specialCharRegex);
    }

    private TextWatcher createTextWatcher(View layout, View messageView) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (layout.getId() == R.id.emailLayout && messageView instanceof TextView) {
                    TextView emailMessage = (TextView) messageView;
                    if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                        emailMessage.setText("올바른 이메일 형식이 아닙니다.");
                        emailMessage.setTextColor(getColor(R.color.red));
                    } else {
                        emailMessage.setText("올바른 이메일입니다.");
                        emailMessage.setTextColor(getColor(R.color.green));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    private void togglePasswordVisibility(EditText editText, View button) {
        if (editText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            button.setBackgroundResource(R.drawable.eye);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            button.setBackgroundResource(R.drawable.eye_off);
        }
    }
}