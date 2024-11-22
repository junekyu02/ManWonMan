package com.example.manwon;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Regist_Item_Category extends AppCompatActivity {

    private ImageButton button1, button2, button3, button4, button5;

    // 선택된 버튼을 추적하기 위함
    private ImageButton selectedButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_item_category);



        ImageButton backButton = findViewById(R.id.regist_back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // 버튼들에 대한 클릭 리스너를 설정
        button1 = findViewById(R.id.category_item_button1_off);
        button2 = findViewById(R.id.category_item_button2_off);
        button3 = findViewById(R.id.category_item_button3_off);
        button4 = findViewById(R.id.category_item_button4_off);
        button5 = findViewById(R.id.category_item_button5_off);


        // 각 버튼에 대한 클릭 리스너 설정
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(button1, new ItemCategoryFragment1());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(button2, new ItemCategoryFragment2());
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(button3, new ItemCategoryFragment3());
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(button4, new ItemCategoryFragment4());
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(button5, new ItemCategoryFragment5());
            }
        });

        // 첫 번째 Fragment를 첫 번째 버튼이 눌린 상태로 보여주기
        if (savedInstanceState == null) {
            handleButtonClick(button1, new ItemCategoryFragment1());
        }
    }

    // Fragment를 교체 메서드
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.category_details_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // 버튼 클릭 시 이미지 변경 및 Fragment 교체
    private void handleButtonClick(ImageButton clickedButton, Fragment fragment){
        // 같은 버튼 클릭 시 변화 X
        if(selectedButton == clickedButton){

        }

        changeButtonImage(clickedButton);
        selectedButton = clickedButton;
        replaceFragment(fragment);
    }

    // 버튼 클릭 시 이미지 변경 메서드
    private void changeButtonImage(ImageButton clickedButton){

        resetButtonImages();    // 일단 모든 버튼 이미지를 off 상태로 하고

        if(clickedButton == button1){
            button1.setImageResource(R.drawable.category_item_button1_on);
        }else if (clickedButton == button2){
            button2.setImageResource(R.drawable.category_item_button2_on);
        }else if (clickedButton == button3){
            button3.setImageResource(R.drawable.category_item_button3_on);
        }else if (clickedButton == button4){
            button4.setImageResource(R.drawable.category_item_button4_on);
        }else if (clickedButton == button5){
            button5.setImageResource(R.drawable.category_item_button5_on);
        }
    }

    // 모든 버튼의 이미지를 원래 상태로 복원
    private void resetButtonImages(){
        button1.setImageResource(R.drawable.category_item_button1_off);
        button2.setImageResource(R.drawable.category_item_button2_off);
        button3.setImageResource(R.drawable.category_item_button3_off);
        button4.setImageResource(R.drawable.category_item_button4_off);
        button5.setImageResource(R.drawable.category_item_button5_off);
    }
}