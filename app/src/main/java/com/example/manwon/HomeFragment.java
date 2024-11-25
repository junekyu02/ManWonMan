package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                // ItemRegistraionActivity로 이동
                Intent intent = new Intent(getActivity(), ItemRegistrationActivity.class);
                startActivity(intent);
            }
        });

        // button3에 클릭 리스너 설정
        ImageButton button3 = view.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ItemChangeActivity로 이동
                Intent intent = new Intent(getActivity(), ItemExchangeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}