package com.example.manwon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExchangeItem_Adapter extends RecyclerView.Adapter<ExchangeItem_Adapter.ExchangeItemViewHolder> {

    private List<ExchangeItem_Model> itemList;

    // 생성자
    public ExchangeItem_Adapter(List<ExchangeItem_Model> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ExchangeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exchange, parent, false);
        return new ExchangeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExchangeItemViewHolder holder, int position) {
        ExchangeItem_Model item = itemList.get(position);
        holder.itemCategoryType.setText(item.getItemCategoryType());
        holder.itemTitle.setText(item.getItemTitle());
        holder.itemDetailsText.setText(item.getItemDetailsText());
        holder.itemImage.setImageResource(item.getItemImageResId());

        // 찜하기 버튼의 상태 설정
        if (item.isBookmarked()) {
            holder.marking.setImageResource(R.drawable.marking_on);  // 찜한 상태
        } else {
            holder.marking.setImageResource(R.drawable.marking_off); // 찜하지 않은 상태
        }

        // 찜하기 버튼 클릭 이벤트
        holder.marking.setOnClickListener(v -> {
            boolean isBookmarked = !item.isBookmarked(); // 찜 상태를 반전
            item.setBookmarked(isBookmarked); // 모델에 반영

            // 버튼의 이미지 변경 (찜 상태에 따라)
            if (isBookmarked) {
                holder.marking.setImageResource(R.drawable.marking_on);  // 찜 상태
                // "찜하기 성공!" 토스트 메시지 표시
                Toast.makeText(v.getContext(), "찜하기 성공!", Toast.LENGTH_SHORT).show();
            } else {
                holder.marking.setImageResource(R.drawable.marking_off); // 찜 안 상태
                // "찜하기 취소!" 토스트 메시지 표시
                Toast.makeText(v.getContext(), "찜하기 취소!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder 클래스
    public static class ExchangeItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemCategoryType;
        TextView itemTitle;
        TextView itemDetailsText;
        ImageView itemImage;
        ImageButton marking;

        public ExchangeItemViewHolder(View itemView) {
            super(itemView);
            itemCategoryType = itemView.findViewById(R.id.itemCategoryType);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDetailsText = itemView.findViewById(R.id.itemDetailsText);
            itemImage = itemView.findViewById(R.id.itemImage);
            marking = itemView.findViewById(R.id.marking);
        }
    }
}