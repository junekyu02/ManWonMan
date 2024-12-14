//package com.example.manwon;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class ExchangeItem_Adapter extends RecyclerView.Adapter<ExchangeItem_Adapter.ExchangeItemViewHolder> {
//
//    private List<ExchangeItem_Model> itemList;
//
//    // 생성자
//    public ExchangeItem_Adapter(List<ExchangeItem_Model> itemList) {
//        this.itemList = itemList;
//    }
//
//    @Override
//    public ExchangeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exchange, parent, false);
//        return new ExchangeItemViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ExchangeItemViewHolder holder, int position) {
//        ExchangeItem_Model item = itemList.get(position);
//        holder.itemCategoryType.setText(item.getItemCategoryType());
//        holder.itemTitle.setText(item.getItemTitle());
//        holder.itemDetailsText.setText(item.getItemDetailsText());
//        holder.itemImage.setImageResource(item.getItemImageResId());
//
//        // 찜하기 버튼의 상태 설정
//        if (item.isBookmarked()) {
//            holder.marking.setImageResource(R.drawable.marking_on);  // 찜한 상태
//        } else {
//            holder.marking.setImageResource(R.drawable.marking_off); // 찜하지 않은 상태
//        }
//
//        // 찜하기 버튼 클릭 이벤트
//        holder.marking.setOnClickListener(v -> {
//            boolean isBookmarked = !item.isBookmarked(); // 찜 상태를 반전
//            item.setBookmarked(isBookmarked); // 모델에 반영
//
//            // 버튼의 이미지 변경 (찜 상태에 따라)
//            if (isBookmarked) {
//                holder.marking.setImageResource(R.drawable.marking_on);  // 찜 상태
//                // "찜하기 성공!" 토스트 메시지 표시
//                Toast.makeText(v.getContext(), "찜하기 성공!", Toast.LENGTH_SHORT).show();
//            } else {
//                holder.marking.setImageResource(R.drawable.marking_off); // 찜 안 상태
//                // "찜하기 취소!" 토스트 메시지 표시
//                Toast.makeText(v.getContext(), "찜하기 취소!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return itemList.size();
//    }
//
//    // ViewHolder 클래스
//    public static class ExchangeItemViewHolder extends RecyclerView.ViewHolder {
//
//        TextView itemCategoryType;
//        TextView itemTitle;
//        TextView itemDetailsText;
//        ImageView itemImage;
//        ImageButton marking;
//
//        public ExchangeItemViewHolder(View itemView) {
//            super(itemView);
//            itemCategoryType = itemView.findViewById(R.id.itemCategoryType);
//            itemTitle = itemView.findViewById(R.id.item_title);
//            itemDetailsText = itemView.findViewById(R.id.itemDetailsText);
//            itemImage = itemView.findViewById(R.id.itemImage);
//            marking = itemView.findViewById(R.id.marking);
//        }
//    }
//}

package com.example.manwon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExchangeItem_Adapter extends RecyclerView.Adapter<ExchangeItem_Adapter.ExchangeItemViewHolder> {

    private List<ExchangeItem_Model> itemList;

    // 생성자
    public ExchangeItem_Adapter(List<ExchangeItem_Model> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ExchangeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exchange, parent, false);
        return new ExchangeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExchangeItemViewHolder holder, int position) {
        ExchangeItem_Model item = itemList.get(position);

        // 데이터 바인딩
        holder.itemCategoryType.setText(item.getItemCategoryType());
        holder.itemTitle.setText(item.getItemTitle());
        holder.itemDetailsText.setText(item.getDetails());

        // 이미지 URL을 사용한다면 Glide나 Picasso로 처리 가능
        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            // 예: Glide.with(holder.itemView.getContext()).load(item.getImageUrl()).into(holder.itemImage);
            holder.itemImage.setImageResource(R.drawable.itemregist_gonggu_coupang); ////////////////////////////////////// 기본 이미지 사용
        } else {
            holder.itemImage.setImageResource(R.drawable.itemregist_gonggu_gmarket); ////////////////////////////////////// 기본 이미지
        }

        // 찜 상태 반영
        if (item.isBookmarked()) {
            holder.marking.setImageResource(R.drawable.marking_on);
        } else {
            holder.marking.setImageResource(R.drawable.marking_off);
        }

        // 찜 버튼 클릭 이벤트
        holder.marking.setOnClickListener(v -> {
            boolean isBookmarked = !item.isBookmarked(); // 상태 반전
            item.setBookmarked(isBookmarked); // 데이터 업데이트

            if (isBookmarked) {
                holder.marking.setImageResource(R.drawable.marking_on);
                Toast.makeText(v.getContext(), "찜하기 성공!", Toast.LENGTH_SHORT).show();
            } else {
                holder.marking.setImageResource(R.drawable.marking_off);
                Toast.makeText(v.getContext(), "찜하기 취소!", Toast.LENGTH_SHORT).show();
            }

            // 찜 상태를 Firebase에 저장하려면 여기서 Firebase 업데이트 코드 추가 가능
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * ViewHolder 클래스
     */
    public static class ExchangeItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemCategoryType; // 아이템 유형
        TextView itemTitle; // 제목
        TextView itemDetailsText; // 상세 설명
        ImageView itemImage; // 이미지
        ImageButton marking; // 찜하기 버튼

        public ExchangeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCategoryType = itemView.findViewById(R.id.itemCategoryType);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDetailsText = itemView.findViewById(R.id.itemDetailsText);
            itemImage = itemView.findViewById(R.id.itemImage);
            marking = itemView.findViewById(R.id.marking);
        }
    }

    /**
     * RecyclerView 아이템 삭제 처리
     * @param position 삭제할 아이템의 위치
     */
    public void removeItem(int position) {
        if (position >= 0 && position < itemList.size()) {
            itemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * 특정 위치의 아이템 반환
     * @param position 위치
     * @return 해당 위치의 ExchangeItem_Model 객체
     */
    public ExchangeItem_Model getItem(int position) {
        if (position >= 0 && position < itemList.size()) {
            return itemList.get(position);
        }
        return null;
    }
}
