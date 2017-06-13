package com.example.wedad.design.welcomscreens.mainNavigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.MapsMarker;
import com.example.wedad.design.welcomscreens.PlaceActivity;
import com.example.wedad.design.welcomscreens.adapter.DividerItemDecoration;
import com.example.wedad.design.welcomscreens.adapter.LongRunningOperationDelegate;
import com.example.wedad.design.welcomscreens.adapter.PlacesAdapter;
import com.example.wedad.design.welcomscreens.beanspkg.Place;
import com.example.wedad.design.welcomscreens.parseapipkg.ParseForSquerApi;
import com.example.wedad.design.welcomscreens.singletonepkg.CoordinatesSingleTone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class NearMeFragment extends Fragment implements LongRunningOperationDelegate {
    double lat, log;
    String url;
    ParseForSquerApi parser;
    List<Place> places;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ButtonBarLayout backBtn,mapBtn;
    LinearLayout linlaHeaderProgress;

    public NearMeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NearMeFragment newInstance() {
        NearMeFragment fragment = new NearMeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }
    @Override
    public void onResume() {
        super.onResume();
        linlaHeaderProgress.setVisibility(View.VISIBLE);

        lat = CoordinatesSingleTone.lat;
        log = CoordinatesSingleTone.log;
        System.out.println("lat:-" + lat);
        parser = new ParseForSquerApi(this);

         url= "https://api.foursquare.com/v2/venues/explore?offset=0&limit=50&ll="+lat+","+log+"&radius=5000&client_id=X4QNEBZTSQ5S0ELHVCQ2Z5NUYEB5LPAMS3POXLWTOH3GKEDD&client_secret=3DGXXBTOYY1O20DPXM54UZJYPZWBZTJGPGVCA3OHZVRVZOBD&v=20121215";
      //  url = "https://api.foursquare.com/v2/venues/explore?offset=0&limit=50&ll=31.19,29.90&radius=5000&client_id=X4QNEBZTSQ5S0ELHVCQ2Z5NUYEB5LPAMS3POXLWTOH3GKEDD&client_secret=3DGXXBTOYY1O20DPXM54UZJYPZWBZTJGPGVCA3OHZVRVZOBD&v=20121215";
        parser.setUrl(url);
        parser.getPlaceUsingForSquer();
        System.out.println("ya Rab");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_near_me, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        linlaHeaderProgress = (LinearLayout) v.findViewById(R.id.linlaHeaderProgress);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        backBtn=(ButtonBarLayout)v.findViewById(R.id.back);
        mapBtn=(ButtonBarLayout)v.findViewById(R.id.map);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BottomNavigationView)getActivity().findViewById(R.id.navigation)).setSelectedItemId(R.id.action_home);
            }
        });
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(getContext(), MapsMarker.class);
                Bundle args = new Bundle();
                if(places!=null) {
                    args.putSerializable("choosenPlace", (Serializable) places);
                    detailIntent.putExtras(args);
                    startActivity(detailIntent);
                }
                else
                {

                   System.out.println("Erroooooooooo");
                }
            }
        });
        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    @Override
    public void getListOfPlaces(List<Place> placesl) {
        System.out.println("Hello list" + placesl.size());
        System.out.println("NNOOOOOOOOOOOO");
        places = new ArrayList<>(placesl);
        System.out.println(places.size());
        linlaHeaderProgress.setVisibility(View.GONE);
        HandleBetween();
    }

    void HandleBetween() {
        if (places != null) {
            PlacesAdapter placeAdapter = new PlacesAdapter(places, getContext());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL));

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
            }, getContext()));

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        Intent myIntent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
//        startActivity(myIntent);
        return true;


    }

}
