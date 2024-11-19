package com.example.manwon;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;
    private BottomSheetDialog bottomSheetDialog;
    private OnItemClickListener itemClickListener;

    // 생성자
    public ListViewAdapter(Context context, ArrayList<String> list, BottomSheetDialog bottomSheetDialog, OnItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.bottomSheetDialog = bottomSheetDialog;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0; // 고유 ID가 필요 없으므로 0 반환
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
        TextView textView = view.findViewById(R.id.textView);

        // 리스트 항목 데이터 설정
        String item = list.get(position);
        textView.setText(item);

        // 클릭 이벤트 처리
        textView.setOnClickListener(v -> {
            Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
            itemClickListener.onItemClick(item); // 클릭 시 인터페이스 메서드 호출
        });

        return view;
    }

    // 클릭 이벤트 인터페이스
    public interface OnItemClickListener {
        void onItemClick(String item);
    }
}
