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

    private DatabaseReference locationRef;
    private String userId;
    private String region1 = "설정된 지역1 없음";
    private String region2 = "설정된 지역2 없음";
    private String currentSelectedRegion = null; // 현재 선택된 지역

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_collective_buy);

        locationRef = FirebaseDatabase.getInstance().getReference("location");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        toolbarText = findViewById(R.id.current_location);

        // 지역 데이터 가져오기
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
                    toolbarText.setText(region1);
                    currentSelectedRegion = region1;
                    loadGongguItemsForRegion(region1);
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

        // 알림 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestNotificationPermission();
            }
        }

        ImageButton overflowButton = findViewById(R.id.overflowIcon);
        overflowButton.setOnClickListener(this::showPopupMenu);
    }

    private void loadGongguItemsForRegion(String region) {
        if (region == null || region.isEmpty()) return;
        String regionKey = region.replace(" ", "_");
        DatabaseReference gongguRef = FirebaseDatabase.getInstance().getReference("gongguItems").child(regionKey);

        gongguRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gongguItemList.clear();
                // gongguItems/<region>/<userId>/<itemKey>
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot itemSnapshot : userSnapshot.getChildren()) {
                        GongguItem_Model item = itemSnapshot.getValue(GongguItem_Model.class);
                        if (item != null) {
                            gongguItemList.add(item);
                        }
                    }
                }

                if (adapter == null) {
                    adapter = new GongguItem_Adapter(gongguItemList);
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ItemCollectiveBuyActivity.this, "아이템을 불러오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestNotificationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.POST_NOTIFICATIONS)) {
            new AlertDialog.Builder(this)
                    .setTitle("알림 권한 요청")
                    .setMessage("이 앱은 공동구매 목표 달성 시 알림을 발송합니다. 알림 권한을 허용해주세요.")
                    .setPositiveButton("허용", (dialog, which) -> {
                        ActivityCompat.requestPermissions(ItemCollectiveBuyActivity.this,
                                new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                                REQUEST_CODE_PERMISSION);
                    })
                    .setNegativeButton("거부", null)
                    .show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                    REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 권한 처리 로직 필요시 추가
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(ItemCollectiveBuyActivity.this, view);
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
                loadGongguItemsForRegion(region1);
                return true;
            } else if (item.getItemId() == R.id.option_two) {
                toolbarText.setText(region2);
                currentSelectedRegion = region2;
                loadGongguItemsForRegion(region2);
                return true;
            } else if (item.getItemId() == R.id.option_three) {
                Intent intent = new Intent(ItemCollectiveBuyActivity.this, MapViewActivity.class);
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
