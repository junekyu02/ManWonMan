package com.example.manwon;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ItemCategoryFragment3 extends Fragment {
    public ItemCategoryFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_category3, container, false);

        // category_item_1
        TextView categoryitem1 = rootView.findViewById(R.id.category_item_1);
        categoryitem1.setOnClickListener(v -> {
            String selectedText = "모자";
            int selectedColor = Color.BLACK;

            // 결과 데이터를 1번 액티비티로 전달
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selectedText", selectedText);  // 클릭한 텍스트를 전달
            resultIntent.putExtra("selectedColor", selectedColor);  // 색상 정보 전달
            getActivity().setResult(RESULT_OK, resultIntent);  // 결과를 Activity로 전달
            getActivity().finish();  // 2번 액티비티 종료
        });

        // category_item_2
        TextView categoryitem2 = rootView.findViewById(R.id.category_item_2);
        categoryitem2.setOnClickListener(v -> {
            String selectedText = "아우터";
            int selectedColor = Color.BLACK;

            // 결과 데이터를 1번 액티비티로 전달
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selectedText", selectedText);  // 클릭한 텍스트를 전달
            resultIntent.putExtra("selectedColor", selectedColor);  // 색상 정보 전달
            getActivity().setResult(RESULT_OK, resultIntent);  // 결과를 Activity로 전달
            getActivity().finish();  // 2번 액티비티 종료
        });

        // category_item_3
        TextView categoryitem3 = rootView.findViewById(R.id.category_item_3);
        categoryitem3.setOnClickListener(v -> {
            String selectedText = "상의";
            int selectedColor = Color.BLACK;

            // 결과 데이터를 1번 액티비티로 전달
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selectedText", selectedText);  // 클릭한 텍스트를 전달
            resultIntent.putExtra("selectedColor", selectedColor);  // 색상 정보 전달
            getActivity().setResult(RESULT_OK, resultIntent);  // 결과를 Activity로 전달
            getActivity().finish();  // 2번 액티비티 종료
        });

        // category_item_4
        TextView categoryitem4 = rootView.findViewById(R.id.category_item_4);
        categoryitem4.setOnClickListener(v -> {
            String selectedText = "하의";
            int selectedColor = Color.BLACK;

            // 결과 데이터를 1번 액티비티로 전달
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selectedText", selectedText);  // 클릭한 텍스트를 전달
            resultIntent.putExtra("selectedColor", selectedColor);  // 색상 정보 전달
            getActivity().setResult(RESULT_OK, resultIntent);  // 결과를 Activity로 전달
            getActivity().finish();  // 2번 액티비티 종료
        });

        // category_item_5
        TextView categoryitem5 = rootView.findViewById(R.id.category_item_5);
        categoryitem5.setOnClickListener(v -> {
            String selectedText = "속옷";
            int selectedColor = Color.BLACK;

            // 결과 데이터를 1번 액티비티로 전달
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selectedText", selectedText);  // 클릭한 텍스트를 전달
            resultIntent.putExtra("selectedColor", selectedColor);  // 색상 정보 전달
            getActivity().setResult(RESULT_OK, resultIntent);  // 결과를 Activity로 전달
            getActivity().finish();  // 2번 액티비티 종료
        });

        // category_item_6
        TextView categoryitem6 = rootView.findViewById(R.id.category_item_6);
        categoryitem6.setOnClickListener(v -> {
            String selectedText = "가방";
            int selectedColor = Color.BLACK;

            // 결과 데이터를 1번 액티비티로 전달
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selectedText", selectedText);  // 클릭한 텍스트를 전달
            resultIntent.putExtra("selectedColor", selectedColor);  // 색상 정보 전달
            getActivity().setResult(RESULT_OK, resultIntent);  // 결과를 Activity로 전달
            getActivity().finish();  // 2번 액티비티 종료
        });

        return rootView;
    }
}