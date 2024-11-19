package com.example.manwon;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Address;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.manwon.databinding.ActivityMapViewBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.naver.maps.geometry.LatLng;
//import com.naver.maps.map.CameraAnimation;
//import com.naver.maps.map.CameraUpdate;
//import com.naver.maps.map.LocationTrackingMode;
//import com.naver.maps.map.MapFragment;
//import com.naver.maps.map.NaverMap;
//import com.naver.maps.map.OnMapReadyCallback;
//import com.naver.maps.map.overlay.Marker;
//import com.naver.maps.map.util.FusedLocationSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback, ListViewAdapter.OnItemClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 5000;

    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private final ArrayList<String> nearCity = new ArrayList<>();
    private ActivityMapViewBinding binding;
    private NaverMap naverMap;
    private FusedLocationSource locationSource;
    private ListViewAdapter listViewAdapter;
    private Button addressBtn1;
    private Button addressBtn2;
    private BottomSheetDialog bottomSheetDialog;
    private String addressName = "";
    private final Marker marker = new Marker();
    private double curlatitude = 0.0;
    private double curlongitude = 0.0;
    private String adminArea;
    private LinearLayout btn1bg;
    private LinearLayout btn2bg;
    private ImageButton delbtn1;
    private ImageButton delbtn2;
    private View bottomSheetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map_view);

        binding.previousBtn.setOnClickListener(view -> finish());

        // BottomSheetDialog 초기화
        bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);

        bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet, null);

        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            initMapView();
        }

        btn1bg = findViewById(R.id.btn1bg);
        btn2bg = findViewById(R.id.btn2bg);
        delbtn1 = findViewById(R.id.delbtn1);
        delbtn2 = findViewById(R.id.delbtn2);
        addressBtn1 = findViewById(R.id.addressBtn1);

       // bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        String city1 = getIntent().getStringExtra("지역1");
        String city2 = getIntent().getStringExtra("지역2");

        bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet, null);

        listViewAdapter = new ListViewAdapter(this, nearCity, bottomSheetDialog, this);

        addressBtn1.setOnClickListener(view -> {
            btn2bg.setBackgroundResource(R.drawable.city_button);
            addressBtn1.setTextColor(Color.parseColor("#ffffff"));
            addressBtn2.setTextColor(Color.parseColor("#000000"));

            if (addressBtn1.getText().toString().equals("          +")) {
                if (!addressName.isEmpty() && bottomSheetDialog != null) {
                    readExcel(addressName);
                    ((ListView) bottomSheetView.findViewById(R.id.listView)).setAdapter(listViewAdapter);
                    bottomSheetDialog.setContentView(bottomSheetView);
                    addressBtn1.setText("          +");
                    bottomSheetDialog.show();
                    addressName = "";
                } else {
                    Toast.makeText(this, "No address selected or dialog not initialized.", Toast.LENGTH_SHORT).show();
                }
            } else {
                btn1bg.setBackgroundResource(R.drawable.button_default);
                cameraMove(addressBtn1.getText().toString());
            }
        });



        delbtn1.setOnClickListener(view -> {
            addressBtn1.setText("          +");
            btn1bg.setBackgroundResource(R.drawable.city_button);
            delbtn1.setEnabled(false);
            delbtn1.setVisibility(View.GONE);
            addressBtn1.setTextColor(Color.parseColor("#000000"));
        });

        addressBtn2 = findViewById(R.id.addressBtn2);

        addressBtn2.setOnClickListener(view -> {
            btn1bg.setBackgroundResource(R.drawable.city_button);
            addressBtn1.setTextColor(Color.parseColor("#000000"));
            addressBtn2.setTextColor(Color.parseColor("#ffffff"));
            if (addressBtn2.getText().toString().equals("          +")) {
                if (!addressName.isEmpty()) {
                    readExcel(addressName);
                    ((ListView) bottomSheetView.findViewById(R.id.listView)).setAdapter(listViewAdapter);
                    bottomSheetDialog.setContentView(bottomSheetView);
                    addressBtn2.setText("          +");
                    bottomSheetDialog.show();
                    addressName = "";
                }
            } else {
                addressBtn2.setTextColor(Color.parseColor("#FFFFFF"));
                cameraMove(addressBtn2.getText().toString());
                btn2bg.setBackgroundResource(R.drawable.button_default);
            }
        });

        delbtn2.setOnClickListener(view -> {
            addressBtn2.setText("          +");
            btn2bg.setBackgroundResource(R.drawable.city_button);
            delbtn2.setEnabled(false);
            delbtn2.setVisibility(View.GONE);
            addressBtn2.setTextColor(Color.parseColor("#000000"));
        });

        Button saveBtn = findViewById(R.id.locationSaveBtn);

//        saveBtn.setOnClickListener(view -> {
//            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("location").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//            myRef.removeValue();
//            if (!addressBtn1.getText().toString().equals("          +")) {
//                myRef.push().setValue(addressBtn1.getText().toString());
//            }
//            if (!addressBtn2.getText().toString().equals("          +")) {
//                myRef.push().setValue(addressBtn2.getText().toString());
//            }
//
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra("fromActivity", "MapViewActivity");
//            startActivity(intent);
//            finish();
//        });

        if (city1 != null) {
            addressBtn1.setText(city1);
            btn1bg.setBackgroundResource(R.drawable.button_default);
            delbtn1.setEnabled(true);
            delbtn1.setVisibility(View.VISIBLE);
            addressBtn1.setTextColor(Color.parseColor("#FFFFFF"));
        }
        if (city2 != null) {
            addressBtn2.setText(city2);
            btn2bg.setBackgroundResource(R.drawable.button_default);
            delbtn2.setEnabled(true);
            delbtn2.setVisibility(View.VISIBLE);
            addressBtn2.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    private void cameraMove(String item) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.KOREAN);
        try {
            List<Address> laglng = geocoder.getFromLocationName(item, 1);
            if (laglng != null && !laglng.isEmpty()) {
                CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(
                        new LatLng(laglng.get(0).getLatitude(), laglng.get(0).getLongitude()), 15.0
                ).animate(CameraAnimation.Fly, 3000);

                naverMap.moveCamera(cameraUpdate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(String item) {
        if (addressBtn1.getText().toString().equals("          +")) {
            addressBtn1.setText(item);
            btn1bg.setBackgroundResource(R.drawable.button_default);
            delbtn1.setEnabled(true);
            delbtn1.setVisibility(View.VISIBLE);
            addressBtn1.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (addressBtn2.getText().toString().equals("          +")) {
            addressBtn2.setText(item);
            btn2bg.setBackgroundResource(R.drawable.button_default);
            delbtn2.setEnabled(true);
            delbtn2.setVisibility(View.VISIBLE);
            addressBtn2.setTextColor(Color.parseColor("#FFFFFF"));
        }
        nearCity.clear();
        cameraMove(item);
        bottomSheetDialog.dismiss();
    }

    private void initMapView() {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
    }

    private boolean hasPermission() {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        naverMap.setLocationSource(locationSource);
        naverMap.getUiSettings().setLocationButtonEnabled(true);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        naverMap.setOnMapClickListener((point, coord) -> marker(coord.latitude, coord.longitude));
    }

    private void marker(double latitude, double longitude) {
        marker.setPosition(new LatLng(latitude, longitude));
        marker.setMap(naverMap);

        getAddress(latitude, longitude);
    }

    private void getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.KOREAN);
        Log.d("checheckchcheck", "getAddress occur");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(latitude, longitude, 1, address -> {
                if (address.size() != 0) {
                    if (address.get(0).getSubLocality() != null) {
                        toast(address.get(0).getSubLocality());
                        addressName = address.get(0).getSubLocality();
                    } else {
                        toast(address.get(0).getLocality());
                        addressName = address.get(0).getLocality();
                    }
                    adminArea = address.get(0).getAdminArea();
                    curlatitude = latitude;
                    curlongitude = longitude;
                }
            });
        } else {
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    toast(addresses.get(0).getAdminArea());
                    if (addresses.get(0).getSubLocality() != null) {
                        toast(addresses.get(0).getSubLocality());
                        addressName = addresses.get(0).getSubLocality();
                    } else {
                        toast(addresses.get(0).getLocality());
                        addressName = addresses.get(0).getLocality();
                    }
                    adminArea = addresses.get(0).getAdminArea();
                    curlatitude = latitude;
                    curlongitude = longitude;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("checheckchcheck", "(" + curlatitude + ", " + curlongitude + ")");
    }

    private void toast(String text) {
        runOnUiThread(() -> Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show());
    }

    public void readExcel(String localName) {
        try {
            Workbook wb = Workbook.getWorkbook(getAssets().open("coordinate.xls"));
            if (wb != null) {
                Sheet sheet = null;
                switch (adminArea) {
                    case "서울특별시":
                        sheet = wb.getSheet(0);
                        break;
                    case "강원도":
                        sheet = wb.getSheet(1);
                        break;
                    case "경기도":
                        sheet = wb.getSheet(2);
                        break;
                    case "경상남도":
                        sheet = wb.getSheet(3);
                        break;
                    case "경상북도":
                        sheet = wb.getSheet(4);
                        break;
                    case "광주광역시":
                        sheet = wb.getSheet(5);
                        break;
                    case "대구광역시":
                        sheet = wb.getSheet(6);
                        break;
                    case "대전광역시":
                        sheet = wb.getSheet(7);
                        break;
                    case "부산광역시":
                        sheet = wb.getSheet(8);
                        break;
                    case "세종특별자치시":
                        sheet = wb.getSheet(9);
                        break;
                    case "울산광역시":
                        sheet = wb.getSheet(10);
                        break;
                    case "전라남도":
                        sheet = wb.getSheet(11);
                        break;
                    case "전라북도":
                        sheet = wb.getSheet(12);
                        break;
                    case "제주특별자치도":
                        sheet = wb.getSheet(13);
                        break;
                    case "충청남도":
                        sheet = wb.getSheet(14);
                        break;
                    case "충청북도":
                        sheet = wb.getSheet(15);
                        break;
                    case "인천광역시":
                        sheet = wb.getSheet(16);
                        break;
                }

                if (sheet == null) {
                    Log.e("READ_EXCEL", "Sheet not found for adminArea: " + adminArea);
                    return;
                }

                nearCity.clear();
                int rowTotal = sheet.getRows() - 1;
                Log.d("READ_EXCEL", "Total rows: " + rowTotal);

                for (int row = 1; row <= rowTotal; row++) {
                    String contents = sheet.getCell(1, row).getContents();
                    if (contents.equals(localName)) {
                        String x = sheet.getCell(5, row).getContents();
                        String y = sheet.getCell(6, row).getContents();
                        if (x.isEmpty() || y.isEmpty()) {
                            Log.e("READ_EXCEL", "Invalid coordinates at row: " + row);
                            continue;
                        }
                        double latitude = Double.parseDouble(x);
                        double longitude = Double.parseDouble(y);

                        if (calculateDistance(curlatitude, curlongitude, latitude, longitude) < 10000.0) {
                            String cityName = sheet.getCell(2, row).getContents();
                            nearCity.add(localName + " " + cityName);
                        }
                    }
                }

                listViewAdapter.notifyDataSetChanged();
            }
        } catch (IOException | BiffException e) {
            Log.e("READ_EXCEL", "Error reading Excel file", e);
        }
    }


    private float calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        Location locationA = new Location("point A");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lon1);

        Location locationB = new Location("point B");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lon2);

        return locationA.distanceTo(locationB);
    }
}
