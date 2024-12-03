package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public HomeFragment() {}

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    // HomeFragment가 처음 생성될 때 호출되는 메서드
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragment의 레이아웃을 설정 (activity_home 레이아웃을 사용)
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // button1에 클릭 리스너 설정
        ImageButton button1 = view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ItemCollectiveBuyActivity로 이동
                Intent intent = new Intent(getActivity(), ItemCollectiveBuyActivity.class);
                startActivity(intent);
            }
        });

        // button2에 클릭 리스너 설정
        ImageButton button2 = view.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ItemRegistrationActivity로 이동
                Intent intent = new Intent(getActivity(), ItemRegistrationActivity.class);
                startActivity(intent);
            }
        });

        // button3에 클릭 리스너 설정
        ImageButton button3 = view.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ItemExchangeActivity로 이동
                Intent intent = new Intent(getActivity(), ItemExchangeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}