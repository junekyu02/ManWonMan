package com.example.manwon;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemExchangeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExchangeItem_Adapter adapter;
    private List<ExchangeItem_Model> exchangeItemList;
    TextView toolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_exchange);

        // 툴바 텍스트와 리사이클러뷰 연결
        toolbarText = findViewById(R.id.current_location);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exchangeItemList = new ArrayList<>();
        // 데이터 추가 (아이템 목록)
        exchangeItemList.add(new ExchangeItem_Model("생활용품", "남는 티슈 교환합니다", "상세 내용 더보기", R.drawable.marking_on, false));
        exchangeItemList.add(new ExchangeItem_Model("식품", "남은 음식 교환합니다", "상세 내용 더보기", R.drawable.marking_on, false));
        exchangeItemList.add(new ExchangeItem_Model("가전/디지털", "가전 교환합니다", "상세 내용 더보기", R.drawable.marking_on, false));
        exchangeItemList.add(new ExchangeItem_Model("생활용품", "남는 티슈 교환합니다", "상세 내용 더보기", R.drawable.marking_on, false));
        exchangeItemList.add(new ExchangeItem_Model("식품", "남은 음식 교환합니다", "상세 내용 더보기", R.drawable.marking_on, false));
        exchangeItemList.add(new ExchangeItem_Model("가전/디지털", "가전 교환합니다", "상세 내용 더보기", R.drawable.marking_on, false));
        exchangeItemList.add(new ExchangeItem_Model("생활용품", "남는 티슈 교환합니다", "상세 내용 더보기", R.drawable.marking_on, false));
        exchangeItemList.add(new ExchangeItem_Model("식품", "남은 음식 교환합니다", "상세 내용 더보기", R.drawable.marking_on, false));
        exchangeItemList.add(new ExchangeItem_Model("가전/디지털", "가전 교환합니다", "상세 내용 더보기", R.drawable.marking_on, false));

        // 어댑터 설정
        adapter = new ExchangeItem_Adapter(exchangeItemList);
        recyclerView.setAdapter(adapter);

        // 오버플로우 버튼 클릭 시 메뉴 띄우기
        ImageButton overflowButton = findViewById(R.id.overflowIcon); // 오버플로우 버튼
        overflowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v); // 메뉴를 띄우는 메서드 호출
            }
        });
    }

    // 오버플로우 메뉴를 띄우는 메서드
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(ItemExchangeActivity.this, view);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_overflow, popupMenu.getMenu()); // 메뉴 리소스 불러오기

        // 메뉴 항목 클릭 리스너 설정
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.option_one) {
                    toolbarText.setText("첫 번째 항목 클릭됨");
                    return true;
                } else if (item.getItemId() == R.id.option_two) {
                    toolbarText.setText("두 번째 항목 클릭됨");
                    return true;
                } else if (item.getItemId() == R.id.option_three) {
                    toolbarText.setText("세 번째 항목 클릭됨");
                    return true;
                }
                return false;
            }
        });

        popupMenu.show(); // 메뉴 표시
    }
}