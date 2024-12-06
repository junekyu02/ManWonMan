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

        // Firebase 경로 변경: giftcard/gifts
        mDatabase = FirebaseDatabase.getInstance().getReference("giftcard").child("gifts");

        itemList = new ArrayList<>();
        adapter = new Gift_RecycleAdapter(itemList);
        recyclerView.setAdapter(adapter);

        fetchGiftsFromFirebase();

        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(Gift_CafeDessertActivity.this, Gift_Userchange_Activity.class);
            startActivity(intent);
        });
    }

    private void fetchGiftsFromFirebase() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                itemList.clear(); // 기존 리스트 초기화
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Firebase에 저장한 gift 데이터 가져오기
                    String title = dataSnapshot.child("title").getValue(String.class);
                    String itemType = dataSnapshot.child("itemType").getValue(String.class);

                    // 이미지 리소스는 예제라 임시로 sample_image 사용
                    // 실제로는 이미지 URL을 Firebase Storage에서 받아와 로드하거나, 업로드 구조를 변경해야 함.
                    int imageRes = R.drawable.sample_image;

                    Gift_CafeDessertItem item = new Gift_CafeDessertItem(itemType, title, imageRes);
                    itemList.add(item);
                }
                adapter.notifyDataSetChanged(); // 데이터 변경 후 어댑터 갱신
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // 실패 처리
            }
        });
    }
}
