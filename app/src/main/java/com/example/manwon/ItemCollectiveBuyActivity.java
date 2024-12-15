//package com.example.manwon;
//
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.PopupMenu;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ItemCollectiveBuyActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private GongguItem_Adapter adapter;
//    private List<GongguItem_Model> gongguItemList;
//    TextView toolbarText;
//
//    private static final int REQUEST_CODE_PERMISSION = 123;
//
//    private DatabaseReference locationRef;
//    private String userId;
//    private String region1 = "설정된 지역1 없음";
//    private String region2 = "설정된 지역2 없음";
//    private String currentSelectedRegion = null; // 현재 선택된 지역
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_item_collective_buy);
//
//        locationRef = FirebaseDatabase.getInstance().getReference("location");
//        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        toolbarText = findViewById(R.id.current_location);
//
//        // 지역 데이터 가져오기
//        locationRef.child(userId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<String> regions = new ArrayList<>();
//                for (DataSnapshot child : snapshot.getChildren()) {
//                    String val = child.getValue(String.class);
//                    if (val != null) {
//                        regions.add(val);
//                    }
//                }
//
//                if (regions.size() > 0) {
//                    region1 = regions.get(0);
//                    toolbarText.setText(region1);
//                    currentSelectedRegion = region1;
//                    loadGongguItemsForRegion(region1);
//                }
//                if (regions.size() > 1) {
//                    region2 = regions.get(1);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ItemCollectiveBuyActivity.this, "지역 정보를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        gongguItemList = new ArrayList<>();
//
//        // 알림 권한 요청
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
//                    != PackageManager.PERMISSION_GRANTED) {
//                requestNotificationPermission();
//            }
//        }
//
//        ImageButton overflowButton = findViewById(R.id.overflowIcon);
//        overflowButton.setOnClickListener(this::showPopupMenu);
//    }
//
//    private void loadGongguItemsForRegion(String region) {
//        if (region == null || region.isEmpty()) return;
//        String regionKey = region.replace(" ", "_");
//        DatabaseReference gongguRef = FirebaseDatabase.getInstance().getReference("gongguItems").child(regionKey);
//
//        gongguRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                gongguItemList.clear();
//                // gongguItems/<region>/<userId>/<itemKey>
//                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
//                    for (DataSnapshot itemSnapshot : userSnapshot.getChildren()) {
//                        GongguItem_Model item = itemSnapshot.getValue(GongguItem_Model.class);
//                        if (item != null) {
//                            gongguItemList.add(item);
//                        }
//                    }
//                }
//
//                if (adapter == null) {
//                    adapter = new GongguItem_Adapter(gongguItemList);
//                    recyclerView.setAdapter(adapter);
//                } else {
//                    adapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ItemCollectiveBuyActivity.this, "아이템을 불러오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void requestNotificationPermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.POST_NOTIFICATIONS)) {
//            new AlertDialog.Builder(this)
//                    .setTitle("알림 권한 요청")
//                    .setMessage("이 앱은 공동구매 목표 달성 시 알림을 발송합니다. 알림 권한을 허용해주세요.")
//                    .setPositiveButton("허용", (dialog, which) -> {
//                        ActivityCompat.requestPermissions(ItemCollectiveBuyActivity.this,
//                                new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
//                                REQUEST_CODE_PERMISSION);
//                    })
//                    .setNegativeButton("거부", null)
//                    .show();
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
//                    REQUEST_CODE_PERMISSION);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String[] permissions,
//                                           int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        // 권한 처리 로직 필요시 추가
//    }
//
//    private void showPopupMenu(View view) {
//        PopupMenu popupMenu = new PopupMenu(ItemCollectiveBuyActivity.this, view);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_overflow, popupMenu.getMenu());
//
//        MenuItem itemOne = popupMenu.getMenu().findItem(R.id.option_one);
//        MenuItem itemTwo = popupMenu.getMenu().findItem(R.id.option_two);
//        itemOne.setTitle(region1);
//        itemTwo.setTitle(region2);
//
//        popupMenu.setOnMenuItemClickListener(item -> {
//            if (item.getItemId() == R.id.option_one) {
//                toolbarText.setText(region1);
//                currentSelectedRegion = region1;
//                loadGongguItemsForRegion(region1);
//                return true;
//            } else if (item.getItemId() == R.id.option_two) {
//                toolbarText.setText(region2);
//                currentSelectedRegion = region2;
//                loadGongguItemsForRegion(region2);
//                return true;
//            } else if (item.getItemId() == R.id.option_three) {
//                Intent intent = new Intent(ItemCollectiveBuyActivity.this, MapViewActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
//                return true;
//            }
//            return false;
//        });
//
//        popupMenu.show();
//    }
//}

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
import androidx.recyclerview.widget.ItemTouchHelper;
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

    private static final int REQUEST_PERMISSION_READ_STORAGE = 100;
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

        adapter = new GongguItem_Adapter(gongguItemList);
        recyclerView.setAdapter(adapter);

        // 슬라이드로 삭제 기능 추가
        attachSwipeToDelete(recyclerView, adapter);

        // 알림 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestNotificationPermission();
            }
        }

        ImageButton overflowButton = findViewById(R.id.overflowIcon);
        overflowButton.setOnClickListener(this::showPopupMenu);



        checkPermission(); // 권한 확인 및 요청

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13 이상: READ_MEDIA_IMAGES 권한 사용
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_MEDIA_IMAGES},
                        REQUEST_PERMISSION_READ_STORAGE);
            }
        } else {
            // Android 12 이하: READ_EXTERNAL_STORAGE 권한 사용
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_READ_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "스토리지 접근 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "스토리지 접근 권한이 거부되었습니다. 이미지를 로드할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
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
                            // Firebase의 auto-generated key 저장
                            item.setKey(itemSnapshot.getKey());
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


    private void attachSwipeToDelete(RecyclerView recyclerView, GongguItem_Adapter adapter) {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false; // 이동 동작은 지원하지 않음
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                GongguItem_Model item = gongguItemList.get(position);

                // 현재 사용자가 작성한 항목만 삭제 가능
                if (item.getUserId().equals(userId)) {
                    new AlertDialog.Builder(ItemCollectiveBuyActivity.this)
                            .setTitle("삭제 확인")
                            .setMessage("정말로 해당 아이템을 삭제하시겠습니까?")
                            .setPositiveButton("삭제", (dialog, which) -> deleteItemFromFirebase(item, position))
                            .setNegativeButton("취소", (dialog, which) -> adapter.notifyItemChanged(position))
                            .show();
                } else {
                    Toast.makeText(ItemCollectiveBuyActivity.this, "삭제 권한이 없습니다.", Toast.LENGTH_SHORT).show();
                    adapter.notifyItemChanged(position);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void deleteItemFromFirebase(GongguItem_Model item, int position) {
        if (item.getKey() == null) {
            Toast.makeText(ItemCollectiveBuyActivity.this, "아이템의 키를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            adapter.notifyItemChanged(position); // 삭제 취소 시 RecyclerView 갱신
            return;
        }

        String regionKey = currentSelectedRegion.replace(" ", "_");
        DatabaseReference gongguRef = FirebaseDatabase.getInstance().getReference("gongguItems").child(regionKey).child(userId);

        gongguRef.child(item.getKey()).removeValue()
                .addOnSuccessListener(aVoid -> {
                    // Firebase에서 데이터 삭제 성공 시 RecyclerView 상태 유지
                    Toast.makeText(ItemCollectiveBuyActivity.this, "아이템이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged(); // 화면을 갱신하지만 유지
                })
                .addOnFailureListener(e -> {
                    // Firebase 삭제 실패 시 원래 상태로 복구
                    Toast.makeText(ItemCollectiveBuyActivity.this, "아이템 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    adapter.notifyItemChanged(position); // 실패 시 삭제된 아이템 복구
                });
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

