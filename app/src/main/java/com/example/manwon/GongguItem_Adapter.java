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

    // Constructor
    public GongguItem_Adapter(List<GongguItem_Model> gongguItemList) {
        this.gongguItemList = gongguItemList;
    }

    @Override
    public GongguItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 리사이클러뷰 아이템 레이아웃을 지정하여 ViewHolder 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gonggu, parent, false);
        return new GongguItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GongguItemViewHolder holder, int position) {
        GongguItem_Model item = gongguItemList.get(position);

        // 데이터 바인딩
        holder.itemCategoryType.setText(item.getItemCategoryType());
        holder.itemTitle.setText(item.getItemTitle());
        holder.itemDetailsText.setText(item.getItemDetailsText());
        holder.itemImage.setImageResource(item.getItemImageResId());

        // 현재 참여자 수와 목표 참여자 수를 텍스트로 표시
        holder.currentParticipantsText.setText(String.valueOf(item.getCurrentParticipants()));
        holder.targetParticipantsText.setText(String.valueOf(item.getTargetParticipants()));

        // 공동구매 참여하기 버튼의 상태 설정
        if (item.isParticipating()) {
            holder.marking.setImageResource(R.drawable.participant_on);    // 참여한 상태
        } else {
            holder.marking.setImageResource(R.drawable.participant_off);    // 참여하지 않은 상태
        }

        // 클릭 시 Dialog를 띄우기
        holder.marking.setOnClickListener(v -> {
            if (item.isParticipating()) {
                // 참여한 상태일 때, 취소할 것인지 묻는 다이얼로그 띄우기
                showCancelDialog(holder, item, position);
            } else {
                // 참여하지 않은 상태일 때, 참여할 것인지 묻는 다이얼로그 띄우기
                showConfirmationDialog(holder, item, position);
            }
        });
    }

    // 참여하기 다이얼로그 내에서 참여자가 추가될 때
    private void showConfirmationDialog(GongguItemViewHolder holder, GongguItem_Model item, int position) {
        new AlertDialog.Builder(holder.itemView.getContext())
                .setIcon(R.drawable.registration_warning)
                .setTitle("공동구매 참여 신청 재확인")
                .setMessage("정말로 해당 물품의 공동구매에 참여하시겠습니까?\n\n  ※ 목표 참여 수 도달 시 알림이 발송됩니다.")
                .setPositiveButton("확인", (dialog, which) -> {
                    // 참여 상태를 true로 변경
                    item.setParticipating(true);
                    item.setCurrentParticipants(item.getCurrentParticipants() + 1);  // 참여자 수 증가

                    // 버튼 이미지를 "참여" 상태로 변경
                    holder.marking.setImageResource(R.drawable.participant_on);

                    // 현재 참여자 수 갱신
                    holder.currentParticipantsText.setText(String.valueOf(item.getCurrentParticipants()));

                    // 목표 참여자 수에 도달하면 알림 발송
                    if (item.getCurrentParticipants() >= item.getTargetParticipants()) {
                        sendNotification(holder.itemView.getContext(), item.getItemTitle());
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }

    // 참여 취소 다이얼로그 내에서 참여자가 취소될 때
    private void showCancelDialog(GongguItemViewHolder holder, GongguItem_Model item, int position) {
        new AlertDialog.Builder(holder.itemView.getContext())
                .setTitle("공동구매 참여 취소 재확인")
                .setMessage("정말로 해당 물품의 공동구매 참여를 취소하시겠습니까?")
                .setPositiveButton("확인", (dialog, which) -> {
                    // 참여 상태를 false로 변경
                    item.setParticipating(false);
                    item.setCurrentParticipants(item.getCurrentParticipants() - 1);  // 참여자 수 감소

                    // 버튼 이미지를 "비참여" 상태로 변경
                    holder.marking.setImageResource(R.drawable.participant_off);

                    // 현재 참여자 수 갱신
                    holder.currentParticipantsText.setText(String.valueOf(item.getCurrentParticipants()));
                })
                .setNegativeButton("취소", null)
                .show();
    }


    public void sendNotification(Context context, String itemTitle) {
        // 알림 채널 설정 (Android 8.0 이상에서 채널을 설정해야 한다고 함...)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "gonggu_channel",  // 채널 ID
                    "공동구매 알림",     // 채널 이름
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        // 알림 객체 생성
        Notification notification = new NotificationCompat.Builder(context, "gonggu_channel")
                .setContentTitle("공동구매 목표 달성!")
                .setContentText("\""+ itemTitle + "\" 항목이 공동구매 목표 참여자 수에 도달했습니다!")
                .setSmallIcon(R.drawable.gonggu_notification)  // 알림 아이콘
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)  // 기본 우선순위
                .build();

        // 알림 발송
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            // 고유 ID를 사용해서 알림 발송
            int notificationId = itemTitle.hashCode();  // 고유한 ID 생성
            notificationManager.notify(notificationId, notification);
        }
    }




    @Override
    public int getItemCount() {
        return gongguItemList.size();  // 아이템 리스트의 크기 반환
    }

    public static class GongguItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemCategoryType;
        TextView itemTitle;
        TextView itemDetailsText;
        TextView currentParticipantsText;
        TextView targetParticipantsText;
        ImageView itemImage;
        ImageButton marking;

        // ViewHolder는 각각의 아이템 레이아웃을 바인딩합니다.
        public GongguItemViewHolder(View itemView) {
            super(itemView);
            // 레이아웃에서 각 뷰를 찾음
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