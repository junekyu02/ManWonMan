package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    private RecyclerView recyclerView;
    private FeedRVAdapter adapter;
    private List<FeedModel> feedItems;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private Spinner filterSpinner;

    public static Fragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        recyclerView = rootView.findViewById(R.id.rv);
        filterSpinner = rootView.findViewById(R.id.filterSpinner);
        FloatingActionButton floatingButton = rootView.findViewById(R.id.floatingBtn);

        auth = FirebaseAuth.getInstance();
        feedItems = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("feeds");

        adapter = new FeedRVAdapter(feedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        setupSpinner();
        loadFeedItems();

        floatingButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FeedWriteActivity.class);
            startActivity(intent);
        });

        // PopupWindow 초기화
        LayoutInflater popupInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View popupView = popupInflater.inflate(R.layout.activity_popup_feed, null);
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
        View helpImageView2 = rootView.findViewById(R.id.popup_text_feed); // fragment_feed.xml에 정의된 helpImageView2
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
        rootView.setOnTouchListener(new View.OnTouchListener() {
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

        return rootView;
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.feed_filter_options,
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(spinnerAdapter);
        filterSpinner.setOnItemSelectedListener(new FeedFilterListener(feedItems, adapter, auth));
    }

    private void loadFeedItems() {
        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                feedItems.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    FeedModel model = data.getValue(FeedModel.class);
                    if (model != null) {
                        feedItems.add(0, model);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FeedFragment", "Database error: " + error.getMessage());
            }
        });
    }

    private class FeedFilterListener implements AdapterView.OnItemSelectedListener {
        public FeedFilterListener(List<FeedModel> feedItems, FeedRVAdapter adapter, FirebaseAuth auth) {
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
