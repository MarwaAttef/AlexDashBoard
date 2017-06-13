package com.example.wedad.design.welcomscreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.beanspkg.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsMarker extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<Place> PlacesData;
    Intent intent;
    ButtonBarLayout listBtn, backBtn;
    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;
    Intent detailIntent ;
    Bundle args;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_marker);
        //----------------------------------change to intent--------------------------------------------//
        intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        PlacesData = (List<Place>) bundle.getSerializable("choosenPlace");
        System.out.println(PlacesData.size());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //---------------------------Actions-------------------------------------------------------------//
        listBtn = (ButtonBarLayout) findViewById(R.id.list);
        backBtn = (ButtonBarLayout) findViewById(R.id.back);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToNearMeView();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToNearMeView();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //---------------------------------Navigation ----------------------------------------------------//


    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    void backToNearMeView() {
        onBackPressed();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in places and move the camera
        for (int i = 0; i < PlacesData.size(); i++) {
            LatLng place = new LatLng(PlacesData.get(i).getLat(), PlacesData.get(i).getLog());
            mMap.addMarker(new MarkerOptions().position(place).title(PlacesData.get(i).getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 14.0f));
        }

    }

}
