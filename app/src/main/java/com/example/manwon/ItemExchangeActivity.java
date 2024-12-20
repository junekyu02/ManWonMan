package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemExchangeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExchangeItem_Adapter adapter;
    private List<ExchangeItem_Model> exchangeItemList;
    TextView toolbarText;

    // 추가: 지역 정보 로딩을 위한 변수
    private DatabaseReference locationRef;
    private String userId;
    private String region1 = "설정된 지역1 없음";
    private String region2 = "설정된 지역2 없음";
    private String currentSelectedRegion = null; // 현재 선택된 지역

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_exchange);

        // 툴바 텍스트와 리사이클러뷰 연결
        toolbarText = findViewById(R.id.current_location);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exchangeItemList = new ArrayList<>();
        // 기존 테스트용 아이템 추가 로직은 그대로 둡니다.
        exchangeItemList.add(new ExchangeItem_Model("생활용품", "남는 티슈 교환합니다", "상세 내용 더보기", R.drawable.marking_on, false));
        exchangeItemList.add(new ExchangeItem_Model("식품", "남은 음식 교환합니다", "상세 내용 더보기", R.drawable.marking_on, false));
        exchangeItemList.add(new ExchangeItem_Model("가전/디지털", "가전 교환합니다", "상세 내용 더보기", R.drawable.marking_on, false));
        // 필요하다면 추가 아이템도 유지하거나 제거 가능

        // 어댑터 설정
        adapter = new ExchangeItem_Adapter(exchangeItemList);
        recyclerView.setAdapter(adapter);

        // Firebase에서 지역 정보 가져오기
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        locationRef = FirebaseDatabase.getInstance().getReference("location");

        locationRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> regions = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    String val = child.getValue(String.class);
                    if (val != null) {
                        regions.add(val);
                    }
                }

                if (regions.size() > 0) {
                    region1 = regions.get(0);
                    toolbarText.setText(region1); // 첫 번째 지역을 기본으로 표시
                    currentSelectedRegion = region1;
                }
                if (regions.size() > 1) {
                    region2 = regions.get(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ItemExchangeActivity.this, "지역 정보를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 오버플로우 버튼
        ImageButton overflowButton = findViewById(R.id.overflowIcon); // 오버플로우 버튼
        overflowButton.setOnClickListener(this::showPopupMenu);
    }

    // 기존 테스트용 문구("첫 번째 항목 클릭됨" 등)는 제거하고 region1, region2 반영
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(ItemExchangeActivity.this, view);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_overflow, popupMenu.getMenu());

        MenuItem itemOne = popupMenu.getMenu().findItem(R.id.option_one);
        MenuItem itemTwo = popupMenu.getMenu().findItem(R.id.option_two);
        itemOne.setTitle(region1);
        itemTwo.setTitle(region2);

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.option_one) {
                toolbarText.setText(region1);
                currentSelectedRegion = region1;
                // 여기서 region1에 해당하는 지역에 맞는 아이템을 로드하거나 로직 추가 가능
                return true;
            } else if (item.getItemId() == R.id.option_two) {
                toolbarText.setText(region2);
                currentSelectedRegion = region2;
                // 여기서 region2에 해당하는 지역에 맞는 아이템을 로드하거나 로직 추가 가능
                return true;
            } else if (item.getItemId() == R.id.option_three) {
                Intent intent = new Intent(ItemExchangeActivity.this, MapViewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });

        popupMenu.show();
    }
}