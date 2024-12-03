package com.example.manwon;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigation_Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_main);    // 레이아웃 설정 (바탕이 되는 화면)

        // BottomNavigationView 객체 초기화
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // 아이템(각 항목)이 선택될 때 호출되는 리스너 설정
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.home_icon){
                    transferTo(HomeFragment.newInstance());    // 홈 아이콘 클릭 시 HomeFragment로 전환
                    return true;
                }
                if(itemId == R.id.giftcard_icon){
                    transferTo(GiftCardMain_Fragment.newInstance("param1", "param2"));    // 깊카 아이콘 클릭 시 GiftCardMain_Fragment로 전환
                    return true;
                }
                if(itemId == R.id.feed_icon){
                    transferTo(FeedFragment.newInstance("param1", "param2"));    // 피드 아이콘 클릭 시 FeedFragment로 전환
                    return true;
                }
                if(itemId == R.id.Chatting_icon){
                    transferTo(ChattingFragment.newInstance());    // 채팅 아이콘 클릭 시 ChattingFragment로 전환
                    return true;
                }
                if(itemId == R.id.mypage_icon){
                    transferTo(MyPageFragment.newInstance());    // 마이페이지 아이콘 클릭 시 MyPageFragment로 전환
                    return true;
                }
                return false;
            }
        });

        // 이미 선택되어있는데 또 누른 경우 fragment가 다시 초기화시키지 않고 아무일도 하지 않도록 설정
        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });

        // 이 액티비티가 처음 실행될 때 BottomNavigationView 설정 이후에 HomeFragment를 기본으로 표시하도록 함
        transferTo(HomeFragment.newInstance());
    }

    // 동적으로 Fragment를 교체하기 위해 사용
    // 즉, 해당 액티비티에서 특정 버튼을 클릭하면 다른 Fragment를 표시하도록 할 때 이 메서드를 사용.
    private void transferTo(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)    // 기존 Fragment를 새로운 Fragment로 교체
                .commit();
    }
}