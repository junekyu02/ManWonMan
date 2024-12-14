package com.example.manwon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

public class GiftCardMain_Fragment extends Fragment {

    // Argument 이름 정의
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Argument 값을 저장할 변수
    private String mParam1;
    private String mParam2;

    // PopupWindow 변수 선언
    private PopupWindow popupWindow;

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
        View view = inflater.inflate(R.layout.fragment_gift_card_main_, container, false);

        // configureCategoryButtons 메서드 호출
        configureCategoryButtons(view);

        ImageButton giftRegistButton = view.findViewById(R.id.gift_registbutton);
        giftRegistButton.setOnClickListener(v -> {
            // ItemRegist_Gift로 이동하는 Intent 생성
            Intent intent = new Intent(getActivity(), ItemRegist_Gift.class);
            startActivity(intent);
        });

        // Initialize PopupWindow
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.activity_popup_gift, null);
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        // Set PopupWindow background and animations
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        // Apply elevation to PopupWindow
        ViewCompat.setElevation(popupView, 8f); // Adjust elevation as needed

        // Show PopupWindow when the help button is touched
        ImageView helpImageView2 = view.findViewById(R.id.popup_text_gift); // 버튼을 ID로 찾음
        helpImageView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Show PopupWindow
                    popupWindow.showAsDropDown(v, 0, 0);
                    return true;
                }
                return false;
            }
        });

        // Dismiss the PopupWindow when touched outside
        View rootView = view.findViewById(R.id.Root); // root 레이아웃을 ID로 찾음
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

        return view;
    }

    private void configureCategoryButtons(View view) {
        ImageButton btnCafeDessert = view.findViewById(R.id.btn_cafe_dessert);
        btnCafeDessert.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Gift_CafeDessertActivity.class);
            startActivity(intent);
        });

        ImageButton btnChicken = view.findViewById(R.id.btn_chicken);
        btnChicken.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Gift_ChickenActivity.class);
            startActivity(intent);
        });

        ImageButton btnFastFood = view.findViewById(R.id.btn_fast_food);
        btnFastFood.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Gift_FastFoodActivity.class);
            startActivity(intent);
        });

        ImageButton btnTravel = view.findViewById(R.id.btn_travel);
        btnTravel.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Gift_TravelActivity.class);
            startActivity(intent);
        });

        ImageButton btnConvenience = view.findViewById(R.id.btn_convenience);
        btnConvenience.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Gift_ConvenienceActivity.class);
            startActivity(intent);
        });

        ImageButton btnBread = view.findViewById(R.id.btn_bread);
        btnBread.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Gift_BreadActivity.class);
            startActivity(intent);
        });

        ImageButton btnVoucher = view.findViewById(R.id.btn_voucher);
        btnVoucher.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Gift_VoucherActivity.class);
            startActivity(intent);
        });

        ImageButton btnIceCream = view.findViewById(R.id.btn_icecream);
        btnIceCream.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Gift_Ice_CreamActivity.class);
            startActivity(intent);
        });
    }
}
