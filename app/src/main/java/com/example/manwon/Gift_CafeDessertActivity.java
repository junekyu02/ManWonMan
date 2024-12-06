package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Gift_CafeDessertActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Gift_RecycleAdapter adapter;
    private List<Gift_CafeDessertItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_dessert);

        recyclerView = findViewById(R.id.recyclerView_cafe_dessert);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 데이터를 위한 리스트 생성
        itemList = new ArrayList<>();
        itemList.add(new Gift_CafeDessertItem("커피", "커피 마실 사람~", R.drawable.sample_image));
        itemList.add(new Gift_CafeDessertItem("커피", "차 마실 사람~", R.drawable.sample_image));
        itemList.add(new Gift_CafeDessertItem("커피", "커피 마실 사람~", R.drawable.sample_image));
        itemList.add(new Gift_CafeDessertItem("커피", "차 마실 사람~", R.drawable.sample_image));
        itemList.add(new Gift_CafeDessertItem("커피", "커피 마실 사람~", R.drawable.sample_image));
        itemList.add(new Gift_CafeDessertItem("커피", "차 마실 사람~", R.drawable.sample_image));
        // 추가 데이터 삽입 가능

        // 어댑터 설정
        adapter = new Gift_RecycleAdapter(itemList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new Gift_RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 교환하기 버튼 클릭 시 activity_gift_userchange로 이동
                Intent intent = new Intent(Gift_CafeDessertActivity.this, Gift_Userchange_Activity.class);
                startActivity(intent);
            }
        });
    }
}
