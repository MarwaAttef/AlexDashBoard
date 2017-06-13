package com.example.wedad.design.welcomscreens.finance;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.PlaceActivity;
import com.example.wedad.design.welcomscreens.adapter.DividerItemDecoration;
import com.example.wedad.design.welcomscreens.adapter.LongRunningOperationDelegate;
import com.example.wedad.design.welcomscreens.adapter.PlacesAdapter;
import com.example.wedad.design.welcomscreens.beanspkg.Place;
import com.example.wedad.design.welcomscreens.parseapipkg.ParseGoogleApi;
import com.example.wedad.design.welcomscreens.singletonepkg.CoordinatesSingleTone;

import java.util.ArrayList;
import java.util.List;


public class BankFragment extends Fragment implements LongRunningOperationDelegate {
    double lat, log;
    String url;
    ParseGoogleApi parser;
    String apikey = "AIzaSyBCgrtz_IlNsC7jC5sDPaynZyR8VPJ0-tw";
    List<Place> places;
    RecyclerView recyclerView;
    LinearLayout linlaHeaderProgress;

    public BankFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BankFragment newInstance(String param1, String param2) {
        BankFragment fragment = new BankFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bank, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        linlaHeaderProgress = (LinearLayout) v.findViewById(R.id.linlaHeaderProgress);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();
        linlaHeaderProgress.setVisibility(View.VISIBLE);
        lat = CoordinatesSingleTone.lat;
        log = CoordinatesSingleTone.log;
        System.out.println("lat:-" + lat);

            parser = new ParseGoogleApi(this);
            String placeCategory = "bank";
            // url= "https://api.foursquare.com/v2/venues/explore?offset=0&limit=50&query="+placeCategory+"&ll="+lat+","+log+"&radius=5000&client_id=X4QNEBZTSQ5S0ELHVCQ2Z5NUYEB5LPAMS3POXLWTOH3GKEDD&client_secret=3DGXXBTOYY1O20DPXM54UZJYPZWBZTJGPGVCA3OHZVRVZOBD&v=20121215";
            url = "https://maps.googleapis.com/maps/api/place/search/json?&location=" + lat + "," + log + "&radius=2000&sensor=false&key=" + apikey + "&type=" + placeCategory;
            parser.setUrl(url);
            parser.getPlaceUsingForSquer();
            System.out.println("ya Rab");

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
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
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

}
