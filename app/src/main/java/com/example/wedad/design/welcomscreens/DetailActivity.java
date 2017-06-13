package com.example.wedad.design.welcomscreens;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.beanspkg.Place;
import com.example.wedad.design.welcomscreens.singletonepkg.CoordinatesSingleTone;

public class DetailActivity extends AppCompatActivity {
    TextView placeName, tips, mobile, telephone,website;
    Button getDirection;
    double destinationLongitude,destinationLatitude,currentLatitude,currentLongitude;
    Intent intent;
    Place placeData;
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //<< this
//change to intent
        Intent intenth = this.getIntent();
        Bundle bundle = intenth.getExtras();

        placeData=(Place) bundle.getSerializable("choosenPlace");
//--------------------------------------------------------------------------------------------------//
        placeName = (TextView) findViewById(R.id.placeName);
        tips = (TextView) findViewById(R.id.tips);
        mobile=(TextView)findViewById(R.id.mobile);
        telephone=(TextView)findViewById(R.id.telephone);
        website=(TextView)findViewById(R.id.website);
        getDirection=(Button)findViewById(R.id.direction);

//--------------------------------------------------------------------------------------------------//
       currentLatitude= CoordinatesSingleTone.lat;
        currentLongitude=CoordinatesSingleTone.log;
        System.out.println("current"+currentLatitude);
        System.out.println("current"+currentLongitude);
        placeName.setText(placeData.getName());
//--------------------------------------------------------------------------------------------------//

        if(placeData.getAddress()!=null)
        {
            if(placeData.getCrossStreet()!=null)
            {
                tips.setText(placeData.getAddress()+" ,CrossStreet  "+placeData.getCrossStreet());
            }
            else {

                tips.setText(placeData.getAddress());
            }
        }
        else
        {

            tips.setText("No Addres");
        }

        if(placeData.getPhone()==null)
        {
            mobile.setText(placeData.getPhone());
        }

        else
        {
            mobile.setText("No Mobile Number");
        }
        if(placeData.getFormattedPhone()!=null)
        {

            telephone.setText(placeData.getFormattedPhone());
        }
        else
        {

            telephone.setText("No Telephone Number");
        }
        if(placeData.getUrl()!=null)
        {
            website.setText(placeData.getUrl());
            website.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent=new Intent(Intent.ACTION_VIEW, Uri.parse(placeData.getUrl()));
                    startActivity(intent);
                }
            });

        }
        else
        {

            website.setText("No website Available :D ");
        }
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationLatitude=placeData.getLat();
                destinationLongitude=placeData.getLog();
                System.out.println(":D'"+destinationLongitude+destinationLatitude);
                String url;
                url="http://maps.google.com/maps?saddr="+currentLatitude+","+currentLongitude+"&daddr="+destinationLatitude+","+destinationLongitude;
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
