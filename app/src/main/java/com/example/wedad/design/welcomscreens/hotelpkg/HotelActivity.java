package com.example.wedad.design.welcomscreens.hotelpkg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.PlaceActivity;
import com.example.wedad.design.welcomscreens.adapter.DividerItemDecoration;
import com.example.wedad.design.welcomscreens.adapter.LongRunningOperationDelegate;
import com.example.wedad.design.welcomscreens.adapter.PlacesAdapter;
import com.example.wedad.design.welcomscreens.beanspkg.Place;
import com.example.wedad.design.welcomscreens.parseapipkg.ParseForSquerApi;
import com.example.wedad.design.welcomscreens.singletonepkg.CoordinatesSingleTone;

import java.util.ArrayList;
import java.util.List;

public class HotelActivity extends AppCompatActivity implements LongRunningOperationDelegate {
    double lat, log;
    String url;
    ParseForSquerApi parser;
    List<Place> places;
    RecyclerView recyclerView;
    LinearLayout linlaHeaderProgress;
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //<< this
        getSupportActionBar().setTitle("Hotels");  // provide compatibility to all the versions
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
    }


    @Override
    public void onResume() {
        super.onResume();
        linlaHeaderProgress.setVisibility(View.VISIBLE);
        lat = CoordinatesSingleTone.lat;
        log = CoordinatesSingleTone.log;
        System.out.println("lat:-" + lat);

            parser = new ParseForSquerApi(this);
            String placeCategory = "Hotels";

            url = "https://api.foursquare.com/v2/venues/explore?offset=0&limit=50&query=" + placeCategory + "&ll=" + lat + "," + log + "&radius=5000&client_id=X4QNEBZTSQ5S0ELHVCQ2Z5NUYEB5LPAMS3POXLWTOH3GKEDD&client_secret=3DGXXBTOYY1O20DPXM54UZJYPZWBZTJGPGVCA3OHZVRVZOBD&v=20121215";
            // url = "https://api.foursquare.com/v2/venues/explore?offset=0&limit=50&query=" + placeCategory + "&ll=31.19,29.90&radius=5000&client_id=X4QNEBZTSQ5S0ELHVCQ2Z5NUYEB5LPAMS3POXLWTOH3GKEDD&client_secret=3DGXXBTOYY1O20DPXM54UZJYPZWBZTJGPGVCA3OHZVRVZOBD&v=20121215";
            parser.setUrl(url);
            parser.getPlaceUsingForSquer();
            System.out.println("ya Rab");

    }

    //---------------------------------------------------------------------------------------//
    @Override
    public void getListOfPlaces(List<Place> placesl) {
        System.out.println("Hello list" + placesl.size());
        System.out.println("NNOOOOOOOOOOOO");
        places = new ArrayList<>(placesl);
        System.out.println(places.size());
        linlaHeaderProgress.setVisibility(View.GONE);
        HandleBetween();
    }

    //-----------------------------------------------------------------------------------------------//
    void HandleBetween() {

        if (places != null) {
            PlacesAdapter placeAdapter = new PlacesAdapter(places, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

            recyclerView.setAdapter(placeAdapter);
            recyclerView.setAdapter(new PlacesAdapter(places, new PlacesAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Place item) {

                    Intent detailIntent = new Intent(recyclerView.getContext(), PlaceActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("choosenPlace", item);
                    detailIntent.putExtras(args);
                    startActivity(detailIntent);

                }
            }, this));

        }
    }
}
