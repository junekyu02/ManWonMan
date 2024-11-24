package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GiftCardMain_Fragment extends Fragment {

    // Argument 이름 정의
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Argument 값을 저장할 변수
    private String mParam1;
    private String mParam2;

    public GiftCardMain_Fragment() {
        // Required empty public constructor
    }

    public static GiftCardMain_Fragment newInstance(String param1, String param2) {
        GiftCardMain_Fragment fragment = new GiftCardMain_Fragment();
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 기존의 Gift_Card_MainActivity 레이아웃을 inflate
        View view = inflater.inflate(R.layout.activity_gift_card_main, container, false);

        // configureCategoryButtons 메서드 호출
        configureCategoryButtons(view);

        return view;
    }

    private void configureCategoryButtons(View view) {
        ImageButton btnCafeDessert = view.findViewById(R.id.btn_cafe_dessert);
        btnCafeDessert.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CafeDessertActivity.class);
            startActivity(intent);
        });

        ImageButton btnChicken = view.findViewById(R.id.btn_chicken);
        btnChicken.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChickenActivity.class);
            startActivity(intent);
        });

        ImageButton btnFastFood = view.findViewById(R.id.btn_fast_food);
        btnFastFood.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FastFoodActivity.class);
            startActivity(intent);
        });

        ImageButton btnTravel = view.findViewById(R.id.btn_travel);
        btnTravel.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TravelActivity.class);
            startActivity(intent);
        });

        ImageButton btnConvenience = view.findViewById(R.id.btn_convenience);
        btnConvenience.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ConvenienceActivity.class);
            startActivity(intent);
        });

        ImageButton btnBread = view.findViewById(R.id.btn_bread);
        btnBread.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BreadActivity.class);
            startActivity(intent);
        });

        ImageButton btnVoucher = view.findViewById(R.id.btn_voucher);
        btnVoucher.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), VoucherActivity.class);
            startActivity(intent);
        });

        ImageButton btnIceCream = view.findViewById(R.id.btn_icecream);
        btnIceCream.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Ice_CreamActivity.class);
            startActivity(intent);
        });
    }
}

