package com.example.manwon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Gift_RecycleAdapter extends RecyclerView.Adapter<Gift_RecycleAdapter.CafeDessertViewHolder> {

    private List<Gift_CafeDessertItem> itemList;
    private OnItemClickListener listener;

    // 생성자
    public Gift_RecycleAdapter(List<Gift_CafeDessertItem> itemList) {
        this.itemList = itemList;
    }

    // 인터페이스 정의
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // 리스너 설정 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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
        Gift_CafeDessertItem item = itemList.get(position);

        // 데이터 연결
        holder.tagTextView.setText(item.getTag());
        holder.titleTextView.setText(item.getTitle());
        holder.imageView.setImageResource(item.getImageResource());

        // 교환하기 버튼 클릭 리스너 설정
        holder.exchangeButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    // 아이템 개수 반환
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder 클래스 정의
    public static class CafeDessertViewHolder extends RecyclerView.ViewHolder {
        public TextView tagTextView, titleTextView, detailButton;
        public ImageView imageView;
        public ImageButton exchangeButton;

        // 생성자
        public CafeDessertViewHolder(View itemView) {
            super(itemView);
            tagTextView = itemView.findViewById(R.id.gift_tag);
            titleTextView = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.gift_item_image);
            detailButton = itemView.findViewById(R.id.gift_detail);
            exchangeButton = itemView.findViewById(R.id.gift_exchange_button);
        }
    }
}


