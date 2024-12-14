package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public HomeFragment() {}

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    // HomeFragment가 화면에 추가되거나 처음 생성될 때 호출되는 메서드
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragment의 레이아웃을 설정 (fragment_home 레이아웃을 사용)
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

        // PopupWindow 초기화
        LayoutInflater popupInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View popupView = popupInflater.inflate(R.layout.activity_popup_home, null);
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        // PopupWindow 설정
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        // PopupWindow에 그림자 효과 추가
        ViewCompat.setElevation(popupView, 8f); // 필요에 따라 그림자 높이 조정

        // helpImageView2를 눌렀을 때 PopupWindow 표시
        View helpImageView2 = view.findViewById(R.id.popup_text_home); // fragment_home에 정의된 helpImageView2
        helpImageView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // PopupWindow 표시
                    popupWindow.showAsDropDown(v, 0, 0);
                    return true;
                }
                return false;
            }
        });

        // 배경 터치 시 PopupWindow 닫기
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
                return false;
            }
        });

        return view;
    }
}
