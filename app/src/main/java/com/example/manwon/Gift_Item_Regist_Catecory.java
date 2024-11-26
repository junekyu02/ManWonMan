package com.example.manwon;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Gift_Item_Regist_Catecory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_item_regist_category); // XML 파일 연결

        // 각 ImageButton 초기화
        ImageButton button1 = findViewById(R.id.gift_category_item_button1_off);
        ImageButton button2 = findViewById(R.id.gift_category_item_button2_off);
        ImageButton button3 = findViewById(R.id.gift_category_item_button3_off);
        ImageButton button4 = findViewById(R.id.gift_category_item_button4_off);
        ImageButton button5 = findViewById(R.id.gift_category_item_button5_off);
        ImageButton button6 = findViewById(R.id.gift_category_item_button6_off);
        ImageButton button7 = findViewById(R.id.gift_category_item_button7_off);
        ImageButton button8 = findViewById(R.id.gift_category_item_button8_off);

        // 각 버튼 클릭 리스너 설정
        button1.setOnClickListener(v -> loadFragment(new fragment_gift_category1()));
        button2.setOnClickListener(v -> loadFragment(new fragment_gift_category2()));
        button3.setOnClickListener(v -> loadFragment(new fragment_gift_category3()));
        button4.setOnClickListener(v -> loadFragment(new fragment_gift_category4()));
        button5.setOnClickListener(v -> loadFragment(new fragment_gift_catecory5()));
        button6.setOnClickListener(v -> loadFragment(new fragment_gift_catecory6()));
        button7.setOnClickListener(v -> loadFragment(new fragment_gift_catecory7()));
        button8.setOnClickListener(v -> loadFragment(new fragment_gift_category8()));
    }

    // Fragment를 전환하는 메서드
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.category_details_container, fragment); // `category_details_container`는 XML의 Fragment 배치 영역
        transaction.addToBackStack(null); // 뒤로 가기 시 이전 Fragment로 돌아가도록 설정
        transaction.commit();
    }
}
