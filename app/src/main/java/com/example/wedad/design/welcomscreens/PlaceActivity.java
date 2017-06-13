package com.example.wedad.design.welcomscreens;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.beanspkg.Place;
import com.example.wedad.design.welcomscreens.singletonepkg.CoordinatesSingleTone;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PlaceActivity extends AppCompatActivity implements OnMapReadyCallback {
    double destinationLongitude, destinationLatitude, currentLatitude, currentLongitude;
    Intent intent;
    Place placeData;
    private GoogleMap mMap;
    Button checkInBtn, directionBtn, favouriteBtn, callBtn, reviewsBtn, websiteBtn;
    TextView address;
    Intent mobileIntent;
    SQLiteHelper helpher;
    int flag=0;
    int hFlag=0;
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        //------------------------------intilization------------------------------------------------------//
        checkInBtn = (Button) findViewById(R.id.checin1);
        favouriteBtn = (Button) findViewById(R.id.like);
        directionBtn = (Button) findViewById(R.id.direction);
        callBtn = (Button) findViewById(R.id.call);
        reviewsBtn = (Button) findViewById(R.id.reviews);
        websiteBtn = (Button) findViewById(R.id.website);
        address = (TextView) findViewById(R.id.adress);
        //-----------------------------------------------------------------------------------------------//
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //<< this
        //----------------------------------change to intent--------------------------------------------//
        intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        placeData = (Place) bundle.getSerializable("choosenPlace");
        //-------------------------------------------mapfragment-------------------------------------------//
        getSupportActionBar().setTitle(placeData.getName());  // provide compatibility to all the versions
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //----------------------------------------------current lat and log -----------------------------------------------//
        currentLatitude = CoordinatesSingleTone.lat;
        currentLongitude = CoordinatesSingleTone.log;
        System.out.println("current" + currentLatitude);
        System.out.println("current" + currentLongitude);
        //------------------------------actions--------------------------------------//
        if (placeData.getAddress() != null) {
            if (placeData.getCrossStreet() != null) {
                address.setText(placeData.getAddress() + " ,CrossStreet  " + placeData.getCrossStreet());
            } else {

                address.setText("Address:"+placeData.getAddress());
            }
        } else {

            address.setText("No Addres Found");
        }
        helpher = new SQLiteHelper(getApplicationContext());

//        checkInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //-----------------------------HistoryDataBase-----------------------//
//
//                //insert into historya table
//                helpher = new SQLiteHelper(getApplicationContext());
//                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
//                String date = df.format(Calendar.getInstance().getTime());
//                placeData.setDate(date);
//                helpher.insertIntoHistoryDB(placeData);
//                checkInBtn.setBackgroundResource(R.drawable.check_btn_fill);
//            }
//        });
        directionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //-------------------------get direction on mao--------------------//
                destinationLatitude = placeData.getLat();
                destinationLongitude = placeData.getLog();
                System.out.println(":D'" + destinationLongitude + destinationLatitude);
                String url;
                url = "http://maps.google.com/maps?saddr=" + currentLatitude + "," + currentLongitude + "&daddr=" + destinationLatitude + "," + destinationLongitude;
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(intent);
            }
        });




        helpher = new SQLiteHelper(getApplicationContext());




//-------------------------------check in check-----------------------------------//
        Boolean hCheck =helpher.checkPlaceExistenceHistory(placeData.getName());
        Log.i("checkkkkkkkkkkkkkkkkk",hCheck.toString()+placeData.getDate());
        if (hCheck == true) {
            hFlag = 0;
            checkInBtn.setBackgroundResource(R.drawable.check_btn);

        } else {
            checkInBtn.setBackgroundResource(R.drawable.check_btn_fill);
            hFlag = 1;
        }
//----------------------------------------------check in listner-----------------------------------------------------------//

        checkInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //-------------------------checkin database-------------//
                hFlag = 1;
                System.out.println("color");
                checkInBtn.setBackgroundResource(R.drawable.check_btn_fill);
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
                String date = df.format(Calendar.getInstance().getTime());
                placeData.setDate(date);
                helpher.insertIntoHistoryDB(placeData);
                Toast.makeText(getApplicationContext(),
                        "you checked in ^_^"
                        , Toast.LENGTH_LONG).show();
                ///saveindatabase
            }
        });
//---------------------------------------------------------------favourite------------------------------//
        Boolean vCheck = helpher.checkPlaceExistence(placeData.getName());
        if (vCheck == true) {
            flag = 0;
            System.out.println(" dont Color favourite");
            favouriteBtn.setBackgroundResource(R.drawable.fav_btn);

        } else {
            System.out.println(" Color favourite");
            favouriteBtn.setBackgroundResource(R.drawable.fav_btn_fill);
            flag = 1;
        }

        favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //-------------------------favourite database-------------//
                if (flag == 0) {
                    System.out.println("color");
                    favouriteBtn.setBackgroundResource(R.drawable.fav_btn_fill);
                    flag = 1;
                    helpher = new SQLiteHelper(getApplicationContext());
                    Boolean check = helpher.checkPlaceExistence(placeData.getName());
                    if (check == true) {

                        helpher.insertIntoFavouritDB(placeData);

                        ///saveindatabase
                    }
                } else {

                    helpher = new SQLiteHelper(getApplicationContext());
                    helpher.deleteARowFavourit(placeData.getName());
                    flag = 0;
                    System.out.println("remove from data base");
                    //remove
                    favouriteBtn.setBackgroundResource(R.drawable.fav_btn);
                    //like.setImageResource(R.drawable.favourite);
                }

            }
        });
        System.out.println("phone"+placeData.getFormattedPhone());
        System.out.println("phone"+placeData.getPhone());
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((placeData.getFormattedPhone() != null) || (placeData.getPhone() != null)&&((placeData.getFormattedPhone() != " ") || (placeData.getPhone() != " "))) {
                    mobileIntent = new Intent(Intent.ACTION_DIAL);
                    if (placeData.getPhone() != null) {
                        mobileIntent.setData(Uri.parse("tel:" + placeData.getPhone()));
                        getPermissionForMakeCall();
                    } else if (placeData.getFormattedPhone() != null) {

                        mobileIntent.setData(Uri.parse("tel:" + placeData.getFormattedPhone()));
                        getPermissionForMakeCall();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "No Phone Number Available "
                            , Toast.LENGTH_LONG).show();
                    ;
                }
            }
        });

        websiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (placeData.getUrl() != null) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(placeData.getUrl()));
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "No Website available", Toast.LENGTH_LONG).show();
                }
            }
        });
        reviewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (placeData.getTip().getUrl() != null) {
                        System.out.println("place review" + placeData.getTip().getUrl());
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(placeData.getTip().getUrl()));
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Reviews available", Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "No Reviews available", Toast.LENGTH_LONG).show();

                }
            }
        });
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
        // Add a marker in Sydney and move the camera
        LatLng place = new LatLng(placeData.getLat(), placeData.getLog());
        mMap.addMarker(new MarkerOptions().position(place).title(placeData.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(place).zoom(20).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    public void getPermissionForMakeCall() {
        if (placeData.getPhone() != null) {
            if (ActivityCompat.checkSelfPermission(PlaceActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(PlaceActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        1);
                return;
            } else {
                startActivity(mobileIntent);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(mobileIntent);
                } else {
                    Toast.makeText(this,
                            "permission denied, ...:(",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}


