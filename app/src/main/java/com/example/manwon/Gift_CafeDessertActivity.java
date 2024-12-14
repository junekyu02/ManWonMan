package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gift_CafeDessertActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Gift_RecycleAdapter adapter;
    private List<Gift_CafeDessertItem> itemList;
    private DatabaseReference mDatabase; // Firebase reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_dessert);

        recyclerView = findViewById(R.id.recyclerView_cafe_dessert);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Firebase 경로 설정
        mDatabase = FirebaseDatabase.getInstance().getReference("giftcard").child("gifts");

        itemList = new ArrayList<>();
        adapter = new Gift_RecycleAdapter(itemList);
        recyclerView.setAdapter(adapter);

        fetchGiftsFromFirebase();

        adapter.setOnItemClickListener(position -> {
            Gift_CafeDessertItem clickedItem = itemList.get(position);

            // Intent로 데이터 전달
            Intent intent = new Intent(Gift_CafeDessertActivity.this, Gift_Userchange_Activity.class);
            intent.putExtra("giftId", clickedItem.getGiftId());
            intent.putExtra("detail", clickedItem.getDetail());
            intent.putExtra("sellerUid", clickedItem.getSellerUid());
            intent.putExtra("itemTitle", clickedItem.getTitle());
            intent.putExtra("itemType", clickedItem.getTag());
            startActivity(intent);
        });
    }

    private void fetchGiftsFromFirebase() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                itemList.clear(); // 기존 리스트 초기화
                List<String> cafeDessertBrands = Arrays.asList("스타벅스", "이디야", "투썸플레이스", "할리스", "빽다방", "메가커피");

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String giftId = dataSnapshot.getKey(); // Firebase의 key를 giftId로 사용
                    String title = dataSnapshot.child("title").getValue(String.class);
                    String itemType = dataSnapshot.child("itemType").getValue(String.class);
                    String sellerUid = dataSnapshot.child("sellerUid").getValue(String.class);
                    String detail = dataSnapshot.child("details").getValue(String.class); // 상세 설명 가져오기
                    Long timestamp = dataSnapshot.child("timestamp").getValue(Long.class);

                    if (title != null && itemType != null && sellerUid != null && detail != null
                            && cafeDessertBrands.contains(itemType)) {
                        // 이미지 리소스는 샘플 이미지로 설정
                        int imageRes = R.drawable.sample_image;

                        // Gift_CafeDessertItem 객체 생성
                        Gift_CafeDessertItem item = new Gift_CafeDessertItem(
                                giftId, itemType, title, detail, imageRes, sellerUid, timestamp != null ? timestamp : 0);
                        itemList.add(item);
                    }
                }
                adapter.notifyDataSetChanged(); // 데이터 변경 후 어댑터 갱신
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Firebase 데이터 로드 실패 처리
            }
        });
    }
}
