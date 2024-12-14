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

public class Gift_VoucherActivity extends AppCompatActivity {

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
            Intent intent = new Intent(Gift_VoucherActivity.this, Gift_Userchange_Activity.class);
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
                itemList.clear();
                List<String> voucherBrands = Arrays.asList("신세계", "문화상품권", "컬쳐랜드", "해피머니", "북앤라이프", "네이버페이");

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String title = dataSnapshot.child("title").getValue(String.class);
                    String itemType = dataSnapshot.child("itemType").getValue(String.class);
                    String sellerUid = dataSnapshot.child("sellerUid").getValue(String.class);
                    Long timestamp = dataSnapshot.child("timestamp").getValue(Long.class);

                    if (title != null && itemType != null && sellerUid != null && timestamp != null
                            && voucherBrands.contains(itemType)) {
                        int imageRes = R.drawable.sample_image;
                        Gift_CafeDessertItem item = new Gift_CafeDessertItem(itemType, title, imageRes, sellerUid, timestamp);
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
