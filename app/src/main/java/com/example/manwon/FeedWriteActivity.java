package com.example.manwon;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
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

        // Apply edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Bind the layout
        binding = ActivityFeedWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Safely extract extras from the Intent
        String link = getIntent().getStringExtra("article_link");
        String articleTitle = getIntent().getStringExtra("article_title");
        String imageUrl = getIntent().getStringExtra("article_imageUrl");

        if (link == null) link = "";
        if (articleTitle == null) articleTitle = "";
        if (imageUrl == null) imageUrl = "";

        Log.d(TAG, "link : " + link + ", title : " + articleTitle + ", imageUrl : " + imageUrl);

        TextView articleTextView = findViewById(R.id.articleTextView);
        ImageView articleImageArea = findViewById(R.id.articleImageArea);

        articleTextView.setText(articleTitle);

        // Load the image using Glide
        if (!imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .into(articleImageArea);
        }

        // Previous button
        binding.previousBtn.setOnClickListener(v -> finish());

        // Register button
        String finalArticleTitle = articleTitle;
        String finalLink = link;
        String finalImageUrl = imageUrl;
        binding.registerBtn.setOnClickListener(v -> {
            String title = binding.titleEditText.getText().toString();
            String content = binding.contentEditText.getText().toString();

            Log.d(TAG, title);
            Log.d(TAG, content);

            String userId = currentUser != null ? currentUser.getUid() : "";
            String time = getTime();
            DatabaseReference feedRef = database.getReference("feeds");

            // Create a unique ID for the new post
            DatabaseReference newPostRef = feedRef.push();
            String postId = newPostRef.getKey();
            if (postId == null) return;

            FeedModel feed = new FeedModel(postId, userId, title, time, content, finalArticleTitle, finalLink, finalImageUrl, 0, 0);
            newPostRef.setValue(feed);

            finish();
        });
    }

    private String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd(E) HH:mm", Locale.KOREAN);
        return dateFormat.format(Calendar.getInstance().getTime());
    }
}
