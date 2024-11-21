package com.example.manwon;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CafeDessertActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CafeDessertAdapter adapter;
    private List<CafeDessertItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_dessert);

        recyclerView = findViewById(R.id.recyclerView_cafe_dessert);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 데이터를 위한 리스트 생성
        itemList = new ArrayList<>();
        itemList.add(new CafeDessertItem("커피", "커피 마실 사람~", R.drawable.sample_image));
        itemList.add(new CafeDessertItem("커피", "차 마실 사람~", R.drawable.sample_image));
        // 추가 데이터 삽입 가능

        // 어댑터 설정
        adapter = new CafeDessertAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }
}