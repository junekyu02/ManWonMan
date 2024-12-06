package com.example.manwon;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GongguItem_Adapter extends RecyclerView.Adapter<GongguItem_Adapter.GongguItemViewHolder> {
    private List<GongguItem_Model> gongguItemList;

    public GongguItem_Adapter(List<GongguItem_Model> gongguItemList) {
        this.gongguItemList = gongguItemList;
    }

    @Override
    public GongguItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gonggu, parent, false);
        return new GongguItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GongguItemViewHolder holder, int position) {
        GongguItem_Model item = gongguItemList.get(position);

        holder.itemCategoryType.setText(item.getItemCategoryType());
        holder.itemTitle.setText(item.getItemTitle());
        holder.itemDetailsText.setText(item.getItemDetailsText());

        // 이미지 URL을 사용하려면 Glide 등 라이브러리 사용 권장:
        // Glide.with(holder.itemView.getContext()).load(item.getImageUrl()).into(holder.itemImage);
        // 여기서는 일단 기본 아이콘으로:
        holder.itemImage.setImageResource(R.drawable.participant_off);

        holder.currentParticipantsText.setText(String.valueOf(item.getCurrentParticipants()));
        holder.targetParticipantsText.setText(String.valueOf(item.getTargetParticipants()));

        if (item.isParticipating()) {
            holder.marking.setImageResource(R.drawable.participant_on);
        } else {
            holder.marking.setImageResource(R.drawable.participant_off);
        }

        holder.marking.setOnClickListener(v -> {
            if (item.isParticipating()) {
                showCancelDialog(holder, item);
            } else {
                showConfirmationDialog(holder, item);
            }
        });
    }

    private void showConfirmationDialog(GongguItemViewHolder holder, GongguItem_Model item) {
        new AlertDialog.Builder(holder.itemView.getContext())
                .setIcon(R.drawable.registration_warning)
                .setTitle("공동구매 참여 신청 재확인")
                .setMessage("정말로 해당 물품의 공동구매에 참여하시겠습니까?\n\n  ※ 목표 참여 수 도달 시 알림이 발송됩니다.")
                .setPositiveButton("확인", (dialog, which) -> {
                    item.setParticipating(true);
                    item.setCurrentParticipants(item.getCurrentParticipants() + 1);
                    holder.marking.setImageResource(R.drawable.participant_on);
                    holder.currentParticipantsText.setText(String.valueOf(item.getCurrentParticipants()));

                    if (item.getCurrentParticipants() >= item.getTargetParticipants()) {
                        sendNotification(holder.itemView.getContext(), item.getItemTitle());
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void showCancelDialog(GongguItemViewHolder holder, GongguItem_Model item) {
        new AlertDialog.Builder(holder.itemView.getContext())
                .setTitle("공동구매 참여 취소 재확인")
                .setMessage("정말로 해당 물품의 공동구매 참여를 취소하시겠습니까?")
                .setPositiveButton("확인", (dialog, which) -> {
                    item.setParticipating(false);
                    item.setCurrentParticipants(item.getCurrentParticipants() - 1);
                    holder.marking.setImageResource(R.drawable.participant_off);
                    holder.currentParticipantsText.setText(String.valueOf(item.getCurrentParticipants()));
                })
                .setNegativeButton("취소", null)
                .show();
    }

    public void sendNotification(Context context, String itemTitle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "gonggu_channel",
                    "공동구매 알림",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        Notification notification = new NotificationCompat.Builder(context, "gonggu_channel")
                .setContentTitle("공동구매 목표 달성!")
                .setContentText("\"" + itemTitle + "\" 항목이 공동구매 목표 참여자 수에 도달했습니다!")
                .setSmallIcon(R.drawable.gonggu_notification)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            int notificationId = itemTitle.hashCode();
            notificationManager.notify(notificationId, notification);
        }
    }

    @Override
    public int getItemCount() {
        return gongguItemList.size();
    }

    public static class GongguItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemCategoryType;
        TextView itemTitle;
        TextView itemDetailsText;
        TextView currentParticipantsText;
        TextView targetParticipantsText;
        ImageView itemImage;
        ImageButton marking;

        public GongguItemViewHolder(View itemView) {
            super(itemView);
            itemCategoryType = itemView.findViewById(R.id.itemCategoryType);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDetailsText = itemView.findViewById(R.id.itemDetailsText);
            itemImage = itemView.findViewById(R.id.itemImage);
            marking = itemView.findViewById(R.id.marking);
            currentParticipantsText = itemView.findViewById(R.id.currentParticipants);
            targetParticipantsText = itemView.findViewById(R.id.targetParticipants);
        }
    }
}
