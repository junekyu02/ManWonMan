package com.example.manwon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CafeDessertAdapter extends RecyclerView.Adapter<CafeDessertAdapter.CafeDessertViewHolder> {

    private List<CafeDessertItem> itemList;

    // 생성자
    public CafeDessertAdapter(List<CafeDessertItem> itemList) {
        this.itemList = itemList;
    }

    // ViewHolder 생성
    @Override
    public CafeDessertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // XML 레이아웃을 객체로 변환
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cafe_dessert, parent, false);
        return new CafeDessertViewHolder(itemView);
    }

    // ViewHolder에 데이터 바인딩
    @Override
    public void onBindViewHolder(CafeDessertViewHolder holder, int position) {
        CafeDessertItem item = itemList.get(position);

        // 데이터 연결
        holder.tagTextView.setText(item.getTag());
        holder.titleTextView.setText(item.getTitle());
        holder.imageView.setImageResource(item.getImageResource());

        // 버튼 클릭 이벤트 설정 (예: 상세 보기 버튼)
        holder.detailButton.setOnClickListener(v -> {
            // 버튼 클릭 시 처리할 코드
        });
    }

    // 아이템 개수 반환
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder 클래스 정의
    public static class CafeDessertViewHolder extends RecyclerView.ViewHolder {
        public TextView tagTextView, titleTextView;
        public ImageView imageView;
        public ImageButton detailButton, exchangeButton;

        // 생성자
        public CafeDessertViewHolder(View itemView) {
            super(itemView);
            tagTextView = itemView.findViewById(R.id.tag);
            titleTextView = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.item_image);
            detailButton = itemView.findViewById(R.id.detail);
            exchangeButton = itemView.findViewById(R.id.exchange_button);
        }
    }
}
