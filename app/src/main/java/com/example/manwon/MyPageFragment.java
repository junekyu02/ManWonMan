//package com.example.manwon;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.fragment.app.Fragment;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class MyPageFragment extends Fragment {
//
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private TextView nicknameTextView;
//    private TextView emailTextView;
//    private TextView locationTextView;
//    private Button logoutButton;
//    private Button changeLocationButton;
//
//    private DatabaseReference databaseReference;
//    private String userId;
//
//    private String currentLocation = "";
//
//    public static MyPageFragment newInstance(String param1, String param2) {
//        MyPageFragment fragment = new MyPageFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // 프래그먼트의 레이아웃을 설정합니다.
//        View rootView = inflater.inflate(R.layout.fragment_my_page, container, false);
//
//        // Firebase 초기화
//        databaseReference = FirebaseDatabase.getInstance().getReference("locations");
//        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        // UI 초기화
//        nicknameTextView = rootView.findViewById(R.id.userNickname);
//        emailTextView = rootView.findViewById(R.id.userEmail);
//        locationTextView = rootView.findViewById(R.id.userLocation);
//        logoutButton = rootView.findViewById(R.id.logoutButton);
//
//        // Firebase에서 사용자 정보 불러오기
//        loadUserData();
//
//        // 로그아웃 버튼 클릭 리스너
//        logoutButton.setOnClickListener(view -> {
//            FirebaseAuth.getInstance().signOut();
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            startActivity(intent);
//            if (getActivity() != null) {
//                getActivity().finish();
//            }
//        });
//
//        // 파라미터 처리 (만약 필요하면)
//        if (getArguments() != null) {
//            String param1 = getArguments().getString(ARG_PARAM1);
//            String param2 = getArguments().getString(ARG_PARAM2);
//            // param1, param2를 사용하여 필요한 작업 수행
//        }
//
//        return rootView;
//    }
//
//    // Firebase에서 사용자 정보 (닉네임, 이메일) 불러오기
//    private void loadUserData() {
//        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//        emailTextView.setText(email);
//
//        // 닉네임 가져오기 (예시: Firebase 데이터베이스에서 닉네임을 가져오는 방식)
//        databaseReference.child(userId).child("nickname").get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                String nickname = task.getResult().getValue(String.class);
//                nicknameTextView.setText(nickname);
//            } else {
//                Toast.makeText(getActivity(), "닉네임을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Firebase에서 저장된 위치 정보 가져오기
//        databaseReference.child(userId).child("location").get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                String location = task.getResult().getValue(String.class);
//                currentLocation = location != null ? location : "지역 정보 없음";
//                locationTextView.setText(currentLocation);
//            } else {
//                Toast.makeText(getActivity(), "위치 정보를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}

package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyPageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView nicknameTextView;
    private TextView emailTextView;
    private TextView locationTextView;
    private Button logoutButton;

    private DatabaseReference databaseReference;
    private String userId;

    public static MyPageFragment newInstance(String param1, String param2) {
        MyPageFragment fragment = new MyPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 프래그먼트의 레이아웃을 설정합니다.
        View rootView = inflater.inflate(R.layout.fragment_my_page, container, false);

        // Firebase 초기화
        databaseReference = FirebaseDatabase.getInstance().getReference("location");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // UI 초기화
        nicknameTextView = rootView.findViewById(R.id.userNickname);
        emailTextView = rootView.findViewById(R.id.userEmail);
        locationTextView = rootView.findViewById(R.id.userLocation);
        logoutButton = rootView.findViewById(R.id.logoutButton);

        // Firebase에서 사용자 정보 불러오기
        loadUserData();

        // 로그아웃 버튼 클릭 리스너
        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        // 파라미터 처리 (만약 필요하면)
        if (getArguments() != null) {
            String param1 = getArguments().getString(ARG_PARAM1);
            String param2 = getArguments().getString(ARG_PARAM2);
            // param1, param2를 사용하여 필요한 작업 수행
        }

        return rootView;
    }

    // Firebase에서 사용자 정보 (닉네임, 이메일, 위치) 불러오기
    private void loadUserData() {
        // 이메일 설정
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        emailTextView.setText(email);

        // 닉네임 가져오기
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        userRef.child("nickname").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                String nickname = task.getResult().getValue(String.class);
                nicknameTextView.setText(nickname != null ? nickname : "닉네임 없음");
            } else {
                Toast.makeText(getActivity(), "닉네임을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 위치 정보 가져오기
        databaseReference.child(userId).limitToFirst(1).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    String location = snapshot.getValue(String.class);
                    locationTextView.setText(location != null ? location : "위치 정보 없음");
                }
            } else {
                Toast.makeText(getActivity(), "위치 정보를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
