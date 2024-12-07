package com.example.manwon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedRVAdapter extends RecyclerView.Adapter<FeedRVAdapter.ViewHolder> {

    private final List<FeedModel> items;
    private final DatabaseReference databaseReference;
    private final FirebaseAuth auth;

    public FeedRVAdapter(List<FeedModel> items) {
        this.items = items;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("feeds");
        this.auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItems(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView, titleTextView, timeTextView, contentTextView, likesTextView;
        private final CircleImageView imageArea;
        private final ImageView likeBtn, moreVertBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            likesTextView = itemView.findViewById(R.id.likesTextView);
            likeBtn = itemView.findViewById(R.id.likeBtn);
            moreVertBtn = itemView.findViewById(R.id.moreVertBtn);
            imageArea = itemView.findViewById(R.id.imageArea);
        }

        public void bindItems(FeedModel item) {
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(item.getUid());

            // 사용자 이름 가져오기
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String userName = snapshot.child("nickname").getValue(String.class);
                        nameTextView.setText(userName != null ? userName : "Unknown");
                    } else {
                        nameTextView.setText("Unknown");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    nameTextView.setText("Unknown");
                }
            });

            titleTextView.setText(item.getTitle());
            timeTextView.setText(formatTime(item.getTime()));  // 시간 형식화 처리
            contentTextView.setText(item.getContent());
            likesTextView.setText("좋아요 " + item.getLikes() + "개");

            // 하트 버튼 클릭 이벤트
            likeBtn.setImageResource(item.getLikedUsers().contains(auth.getCurrentUser().getUid())
                    ? R.drawable.heart
                    : R.drawable.favorite);

            likeBtn.setOnClickListener(v -> toggleLike(item));
            moreVertBtn.setOnClickListener(v -> showPopupMenu(v, item));
        }

        private void toggleLike(FeedModel item) {
            String currentUserId = auth.getCurrentUser().getUid();
            if (!item.getLikedUsers().contains(currentUserId)) {
                item.setLikes(item.getLikes() + 1);
                item.getLikedUsers().add(currentUserId);
                likeBtn.setImageResource(R.drawable.heart);
            } else {
                item.setLikes(item.getLikes() - 1);
                item.getLikedUsers().remove(currentUserId);
                likeBtn.setImageResource(R.drawable.favorite);
            }
            databaseReference.child(item.getId()).setValue(item);
        }

        private void showPopupMenu(View view, FeedModel item) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.feed_popup);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.delete) {
                    deleteFeedItem(view.getContext(), item);
                    return true;
                }
                return false;
            });
            popupMenu.show();
        }

        private void deleteFeedItem(Context context, FeedModel item) {
            databaseReference.child(item.getId()).removeValue().addOnSuccessListener(aVoid -> {
                items.remove(item);
                notifyDataSetChanged();
            }).addOnFailureListener(e -> {
                // 삭제 실패 시 알림 추후 앱 앱 시연 전에 넣기
            });
        }

        private String formatTime(String time) {
            return time;
        }
    }
}
