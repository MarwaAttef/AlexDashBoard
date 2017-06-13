package com.example.wedad.design.welcomscreens.healthpkg;

import android.content.Context;
import android.content.Intent;
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


public class HospitalFragment extends Fragment implements LongRunningOperationDelegate {

    double lat, log;
    String url;
    ParseGoogleApi parser;
    List<Place> places;
    RecyclerView recyclerView;
    LinearLayout linlaHeaderProgress;
    String apikey = "AIzaSyBCgrtz_IlNsC7jC5sDPaynZyR8VPJ0-tw";
    public HospitalFragment() {
        // Required empty public constructor
    }


    public static HospitalFragment newInstance(String param1, String param2) {
        HospitalFragment fragment = new HospitalFragment();

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
        View v = inflater.inflate(R.layout.fragment_hospital, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        linlaHeaderProgress = (LinearLayout) v.findViewById(R.id.linlaHeaderProgress);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        linlaHeaderProgress.setVisibility(View.VISIBLE);
        lat = CoordinatesSingleTone.lat;
        log = CoordinatesSingleTone.log;
        System.out.println("lat:-" + lat);


            parser = new ParseGoogleApi(this);
            String placeCategory = "hospital";
            url = "https://maps.googleapis.com/maps/api/place/search/json?&location=" + lat + "," + log + "&radius=2000&sensor=false&key=" + apikey + "&type=" + placeCategory;
            //url= "https://api.foursquare.com/v2/venues/explore?offset=0&limit=50&query="+placeCategory+"&ll="+lat+","+log+"&radius=5000&client_id=X4QNEBZTSQ5S0ELHVCQ2Z5NUYEB5LPAMS3POXLWTOH3GKEDD&client_secret=3DGXXBTOYY1O20DPXM54UZJYPZWBZTJGPGVCA3OHZVRVZOBD&v=20121215";
            // url = "https://api.foursquare.com/v2/venues/explore?offset=0&limit=50&query=" + placeCategory + "&ll=31.19,29.90&radius=5000&client_id=X4QNEBZTSQ5S0ELHVCQ2Z5NUYEB5LPAMS3POXLWTOH3GKEDD&client_secret=3DGXXBTOYY1O20DPXM54UZJYPZWBZTJGPGVCA3OHZVRVZOBD&v=20121215";
            parser.setUrl(url);
            parser.getPlaceUsingForSquer();
            System.out.println("ya Rab");

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

            PlacesAdapter placeAdapter = new PlacesAdapter(places, getActivity());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setHasFixedSize(true);
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
