package com.example.manwon;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.manwon.databinding.ActivityFeedWriteBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FeedWriteActivity extends AppCompatActivity {

    private ActivityFeedWriteBinding binding;
    private static final String TAG = "FeedWriteActivity";
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseUser currentUser = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_write);

        binding = ActivityFeedWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 링크를 Intent에서 가져옴
        String link = getIntent().getStringExtra("article_link");
        if (link == null) link = "";  // 링크가 없으면 빈 문자열로 설정

        Log.d(TAG, "link : " + link);

        // 뒤로 가기 버튼
        binding.previousBtn.setOnClickListener(v -> finish());

        // 등록 버튼 클릭 시
        binding.registerBtn.setOnClickListener(v -> {
            String title = binding.titleEditText.getText().toString();
            String content = binding.contentEditText.getText().toString();

            Log.d(TAG, title);
            Log.d(TAG, content);

            String userId = currentUser != null ? currentUser.getUid() : "";
            String time = getTime();
            DatabaseReference feedRef = database.getReference("feeds");

            // 새 피드 아이디 생성
            DatabaseReference newPostRef = feedRef.push();
            String postId = newPostRef.getKey();
            if (postId == null) return;

            // FeedModel 생성 시 기사 제목과 이미지 URL 제거
            FeedModel feed = new FeedModel(postId, userId, title, time, content, 0, 0, currentUser != null ? currentUser.getDisplayName() : "Unknown");
            newPostRef.setValue(feed);

            finish();  // 활동 종료
        });
    }

    private String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd(E) HH:mm", Locale.KOREAN);
        return dateFormat.format(Calendar.getInstance().getTime());
    }
}
