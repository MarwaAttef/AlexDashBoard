package com.example.wedad.design.welcomscreens;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.mainNavigation.FavouritsFragment;
import com.example.wedad.design.welcomscreens.mainNavigation.HistoryFragment;
import com.example.wedad.design.welcomscreens.mainNavigation.HomeFragment;
import com.example.wedad.design.welcomscreens.mainNavigation.NearMeFragment;
import com.example.wedad.design.welcomscreens.singletonepkg.CoordinatesSingleTone;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;
    double currentlatitude;
    double currentlongitude;
    boolean isHome = true;
    Bundle b;
    boolean isNetwork = false;

    @Override
    protected void onResume() {
        super.onResume();
    }

    Intent intent;
    LocationManager service;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isNetwork = isNetworkAvailable();
        if(isNetwork ==false)
        {
            Toast.makeText(getApplicationContext(), "pleas check your connectivity", Toast.LENGTH_LONG).show();
        }
        getSupportActionBar().hide(); //<< this
        service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            showGPSDisabledAlertToUser();
        }
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        }
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        selectedFragment = HomeFragment.newInstance();
        beginTransaction();
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.action_home:
                                selectedFragment = HomeFragment.newInstance();
                                break;
                            case R.id.action_nearMe:
                                isNetwork = isNetworkAvailable();
                                if (isNetwork == true) {
                                    selectedFragment = NearMeFragment.newInstance();
                                } else {
                                    Toast.makeText(getApplicationContext(), "pleas check your connectivity", Toast.LENGTH_LONG).show();
                                }
                                break;
                            case R.id.action_favourit:
                                selectedFragment = FavouritsFragment.newInstance();
                                break;
                            case R.id.action_history:
                                selectedFragment = HistoryFragment.newInstance();
                                break;
                        }
                        beginTransaction();
                        return true;
                    }
                });

    }

    void beginTransaction() {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commitAllowingStateLoss();

    }

    private void getMyLocation() {


                checkPermission();
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
                if (mLastLocation != null) {
                    System.out.println("mlastlocation" + mLastLocation);
                    currentlatitude = mLastLocation.getLatitude();
                    System.out.println("mlastlatitude" + currentlatitude);
                    currentlongitude = mLastLocation.getLongitude();
                    System.out.println("mlongitude" + currentlongitude);
                    CoordinatesSingleTone.lat = (currentlatitude);
                    CoordinatesSingleTone.log = (currentlongitude);
                    if(isHome==true) {
                        selectedFragment = HomeFragment.newInstance();
                        beginTransaction();
                    }
                } else if(mLastLocation==null ) {
                   Toast.makeText(getApplicationContext(),"your Location null until now",Toast.LENGTH_LONG).show();
            }
    }

    ////------------------------------open Gps---------------------------/////
    private void showGPSDisabledAlertToUser() {
        // TODO Auto-generated method stub
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage(
                        "GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",

                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(callGPSSettingIntent, 1);
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    ////---------------------check for location permission --------------//////
    void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            //------------------------------------------------------------------------------
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this,
                            "permission was granted, :)",
                            Toast.LENGTH_LONG).show();
                    getMyLocation();

                } else {
                    Toast.makeText(this,
                            "permission denied, ...:(",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
