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

public class Gift_BreadActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Gift_RecycleAdapter adapter;
    private List<Gift_CafeDessertItem> itemList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_dessert);

        recyclerView = findViewById(R.id.recyclerView_cafe_dessert);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference("giftcard").child("gifts");

        itemList = new ArrayList<>();
        adapter = new Gift_RecycleAdapter(itemList);
        recyclerView.setAdapter(adapter);

        fetchGiftsFromFirebase();

        adapter.setOnItemClickListener(position -> {
            Gift_CafeDessertItem clickedItem = itemList.get(position);

            Intent intent = new Intent(Gift_BreadActivity.this, Gift_Userchange_Activity.class);
            intent.putExtra("giftId", clickedItem.getGiftId());
            intent.putExtra("sellerUid", clickedItem.getSellerUid());
            intent.putExtra("itemTitle", clickedItem.getTitle());
            intent.putExtra("itemType", clickedItem.getTag());
            intent.putExtra("detail", clickedItem.getDetail()); // 상세 설명 전달
            startActivity(intent);
        });
    }

    private void fetchGiftsFromFirebase() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                itemList.clear(); // 기존 리스트 초기화
                List<String> breadBrands = Arrays.asList("파리바게트", "뜌레쥬르", "던킨도너츠", "크리스피크림도넛", "와플대학", "홍루이젠");

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String giftId = dataSnapshot.getKey();
                    String title = dataSnapshot.child("title").getValue(String.class);
                    String itemType = dataSnapshot.child("itemType").getValue(String.class);
                    String sellerUid = dataSnapshot.child("sellerUid").getValue(String.class);
                    String detail = dataSnapshot.child("details").getValue(String.class); // 상세 설명 가져오기
                    Long timestamp = dataSnapshot.child("timestamp").getValue(Long.class);

                    if (title != null && itemType != null && sellerUid != null && detail != null
                            && breadBrands.contains(itemType)) {
                        int imageRes = R.drawable.sample_image;

                        Gift_CafeDessertItem item = new Gift_CafeDessertItem(
                                giftId, itemType, title, detail, imageRes, sellerUid, timestamp != null ? timestamp : 0);
                        itemList.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Firebase 에러 처리
            }
        });
    }
}

