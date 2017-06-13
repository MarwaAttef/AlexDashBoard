package com.example.wedad.design.welcomscreens;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wedad.design.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;


public class SearchFragment extends Fragment {

    SupportPlaceAutocompleteFragment autocompleteFragment;
    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();

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
        View v=inflater.inflate(R.layout.fragment_search, container, false);
        autocompleteFragment  = (SupportPlaceAutocompleteFragment)getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.

                System.out.println("Place: " + place.getName());

                System.out.println("atribution" + place.getAttributions());
                System.out.println("phonenumber" + place.getPhoneNumber());
                System.out.println("placetype" + place.getPlaceTypes());
                System.out.println("placerating" + place.getRating());
                System.out.println("websiteurl" + place.getWebsiteUri());
                Geocoder coder = new Geocoder(getContext());
                List<Address> address;
                LatLng p1 = null;

                try {
                    // May throw an IOException
                    address = coder.getFromLocationName((String) place.getAddress(), 5);
                    if (address == null) {

                    }
                    Address location = address.get(0);
                    location.getLatitude();
                    location.getLongitude();
                    System.out.println("location" + location.getLatitude());

                    System.out.println("location" + location.getLongitude());
                    p1 = new LatLng(location.getLatitude(), location.getLongitude());

                } catch (IOException ex) {

                    ex.printStackTrace();
                }
            }

            @Override
            public void onError(Status status) {

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


}
