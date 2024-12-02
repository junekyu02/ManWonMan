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
        setContentView(R.layout.activity_bottom_navigation_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.home_icon){
                    transferTo(HomeFragment.newInstance("param1","param2"));
                    return true;
                }
                if(itemId == R.id.giftcard_icon){
                    transferTo(GiftCardMain_Fragment.newInstance("param1", "param2"));
                    return true;
                }
                if(itemId == R.id.feed_icon){
                    transferTo(FeedFragment.newInstance("param1", "param2"));
                    return true;

                }
                if(itemId == R.id.mypage_icon){

                }
                return false;
            }
        });

        // 이미 선택되어있는데 또 누른 경우 fragment가 다시 초기화 되지 않고 아무일도 안하도록 하기 위함
        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });

        // 처음 뜰 때는 Home이 뜨도록 함
        transferTo(HomeFragment.newInstance("param1", "param2"));
    }

    // 동적으로 Fragment를 교체하기 위해 사용
    // 즉, 해당 액티비티에서 특정 버튼을 클릭하면 다른 Fragment를 표시하도록 할 때 이 메서드를 사용.
    private void transferTo(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}