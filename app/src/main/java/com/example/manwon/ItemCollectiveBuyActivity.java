package com.example.manwon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

public class ItemCollectiveBuyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GongguItem_Adapter adapter;
    private List<GongguItem_Model> gongguItemList;
    TextView toolbarText;

    private static final int REQUEST_CODE_PERMISSION = 123;

    private DatabaseReference databaseReference;
    private String userId;
    private String region1 = "설정된 지역1 없음";
    private String region2 = "설정된 지역2 없음";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_collective_buy);

        // Firebase Database 초기화
        databaseReference = FirebaseDatabase.getInstance().getReference("location");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        toolbarText = findViewById(R.id.current_location);

        // Firebase에서 지역 데이터 가져오기
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> regions = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    regions.add(child.getValue(String.class));
                }

                // 첫 번째와 두 번째 지역 할당
                if (regions.size() > 0) {
                    region1 = regions.get(0);
                    toolbarText.setText(region1); // 툴바 텍스트 변경
                }
                if (regions.size() > 1) {
                    region2 = regions.get(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ItemCollectiveBuyActivity.this, "지역 정보를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gongguItemList = new ArrayList<>();
        // 데이터 추가 (아이템 목록)
        gongguItemList.add(new GongguItem_Model("생활용품", "남는 티슈 공동구매 ㄱㄱ", "상세 내용 더보기", R.drawable.participant_off, false, 0, 1));
        gongguItemList.add(new GongguItem_Model("식품", "컵라면 공구하실분~", "상세 내용 더보기", R.drawable.participant_off, false, 0, 30));
        gongguItemList.add(new GongguItem_Model("가전/디지털", "커피포트 공구해용", "상세 내용 더보기", R.drawable.participant_off, false, 0, 30));
        gongguItemList.add(new GongguItem_Model("생활용품", "알람시계 공구하실분~", "상세 내용 더보기", R.drawable.participant_off, false, 0, 30));
        gongguItemList.add(new GongguItem_Model("식품", "햇반 공동구매 하실 분 구해요", "상세 내용 더보기", R.drawable.participant_off, false, 0, 30));
        gongguItemList.add(new GongguItem_Model("가전/디지털", "무소음 마우스 공구해요", "상세 내용 더보기", R.drawable.participant_off, false, 0, 30));
        gongguItemList.add(new GongguItem_Model("생활용품", "다이소 박스 테이프 인당 4개씩 분할해서 공동구매 하실 분?", "상세 내용 더보기", R.drawable.participant_off, false, 0, 30));
        gongguItemList.add(new GongguItem_Model("생활용품", "남는 티슈 공동구매 ㄱㄱ", "상세 내용 더보기", R.drawable.participant_off, false, 0, 30));
        gongguItemList.add(new GongguItem_Model("식품", "컵라면 공구하실분~", "상세 내용 더보기", R.drawable.participant_off, false, 0, 30));
        gongguItemList.add(new GongguItem_Model("가전/디지털", "커피포트 공구해용", "상세 내용 더보기", R.drawable.participant_off, false, 0, 30));
        gongguItemList.add(new GongguItem_Model("생활용품", "알람시계 공구하실분~", "상세 내용 더보기", R.drawable.participant_off, false, 0, 30));
        gongguItemList.add(new GongguItem_Model("식품", "햇반 공동구매 하실 분 구해요", "상세 내용 더보기", R.drawable.participant_off, false, 0, 30));
        gongguItemList.add(new GongguItem_Model("가전/디지털", "무소음 마우스 공구해요", "상세 내용 더보기", R.drawable.participant_off, false, 0, 30));
        gongguItemList.add(new GongguItem_Model("생활용품", "키친타올 공구하실분?", "상세 내용 더보기", R.drawable.participant_off, false, 0, 30));

        // 어댑터 설정
        adapter = new GongguItem_Adapter(gongguItemList);
        recyclerView.setAdapter(adapter);


        // 알림 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13 이상에서 알림 권한 요청
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                // 권한이 없으면 권한 요청 다이얼로그 표시
                requestNotificationPermission();
            }
        }


        // 오버플로우 버튼 클릭 시 메뉴 띄우기
        ImageButton overflowButton = findViewById(R.id.overflowIcon); // 오버플로우 버튼
        overflowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v); // 메뉴를 띄우는 메서드 호출
            }
        });
    }

    private void requestNotificationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.POST_NOTIFICATIONS)) {
            // 사용자에게 왜 권한이 필요한지 설명하는 다이얼로그 띄우기
            new AlertDialog.Builder(this)
                    .setTitle("알림 권한 요청")
                    .setMessage("이 앱은 공동구매 목표 달성 시 알림을 발송합니다. 알림 권한을 허용해주세요.")
                    .setPositiveButton("허용", (dialog, which) -> {
                        // 권한 요청
                        ActivityCompat.requestPermissions(ItemCollectiveBuyActivity.this,
                                new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                                REQUEST_CODE_PERMISSION);
                    })
                    .setNegativeButton("거부", null)
                    .show();
        } else {
            // 권한 요청 다이얼로그 띄우기
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                    REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 허용됨
                Toast.makeText(this, "알림 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                // 권한 거부됨
                Toast.makeText(this, "알림 권한이 거부되었습니다. 알림 기능을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(ItemCollectiveBuyActivity.this, view);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_overflow, popupMenu.getMenu()); // 메뉴 리소스 불러오기

        // 메뉴 텍스트 동적 변경
        MenuItem itemOne = popupMenu.getMenu().findItem(R.id.option_one);
        MenuItem itemTwo = popupMenu.getMenu().findItem(R.id.option_two);
        itemOne.setTitle(region1);
        itemTwo.setTitle(region2);

        // 메뉴 항목 클릭 리스너 설정
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.option_one) {
                    toolbarText.setText(region1);
                    return true;
                } else if (item.getItemId() == R.id.option_two) {
                    toolbarText.setText(region2);
                    return true;
                } else if (item.getItemId() == R.id.option_three) {
                    // 모든 액티비티 종료 후 MapView로 이동
                    Intent intent = new Intent(ItemCollectiveBuyActivity.this, MapViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // 현재 액티비티도 종료
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }
}