package com.example.wedad.design.welcomscreens.homepkg;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.attractionpkg.AttractiveActivity;
import com.example.wedad.design.welcomscreens.emergencypkg.EmergencyActivity;
import com.example.wedad.design.welcomscreens.enterteinmentpkg.EntertainmentActivity;
import com.example.wedad.design.welcomscreens.finance.FinanceActivity;
import com.example.wedad.design.welcomscreens.food.FoodActivity;
import com.example.wedad.design.welcomscreens.healthpkg.HealthActivity;
import com.example.wedad.design.welcomscreens.hotelpkg.HotelActivity;
import com.example.wedad.design.welcomscreens.religionpkg.ReligionActivity;
import com.example.wedad.design.welcomscreens.school.SchoolActivity;


public class DashBoard extends Fragment {
    ExpandableHeightGridView gridView;
    boolean isNetwork = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        gridView = (ExpandableHeightGridView) view.findViewById(R.id.gridview);

        gridView.setAdapter(new Adapter(gridView.getContext()));


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                isNetwork = isNetworkAvailable();
                                                switch (position) {
                                                    case 0:
                                                        Intent foodIntent = new Intent(getActivity(), FoodActivity.class);

                                                        //Toast.makeText(gridView.getContext(), "Position: " + position, Toast.LENGTH_SHORT).show() ;
                                                        if (isNetwork == true) {
                                                            startActivity(foodIntent);
                                                        } else {
                                                            Toast.makeText(getActivity().getApplicationContext(), "pleas check your connectivity", Toast.LENGTH_LONG).show();
                                                        }
                                                        break;
                                                    case 1:
                                                        Intent emergencyIntent = new Intent(getActivity(), EmergencyActivity.class);
                                                        if (isNetwork == true) {
                                                            startActivity(emergencyIntent);
                                                        } else {
                                                            Toast.makeText(getActivity().getApplicationContext(), "pleas check your connectivity", Toast.LENGTH_LONG).show();
                                                        }
                                                        break;

                                                    case 2:
                                                        Intent attractiveIntent = new Intent(getActivity(), AttractiveActivity.class);
                                                        if (isNetwork == true) {
                                                            startActivity(attractiveIntent);
                                                        } else {
                                                            Toast.makeText(getActivity().getApplicationContext(), "pleas check your connectivity", Toast.LENGTH_LONG).show();
                                                        }
                                                        break;

                                                    case 3:
                                                        Intent financeIntent = new Intent(getActivity(), FinanceActivity.class);
                                                        if (isNetwork == true) {
                                                            startActivity(financeIntent);
                                                        } else {
                                                            Toast.makeText(getActivity().getApplicationContext(), "pleas check your connectivity", Toast.LENGTH_LONG).show();
                                                        }
                                                        break;

                                                    case 4:
                                                        Intent healthIntent = new Intent(getActivity(), HealthActivity.class);

                                                        if (isNetwork == true) {
                                                            startActivity(healthIntent);
                                                        } else {
                                                            Toast.makeText(getActivity().getApplicationContext(), "pleas check your connectivity", Toast.LENGTH_LONG).show();
                                                        }
                                                        break;

                                                    case 5:
                                                        Intent entertainmentIntent = new Intent(getActivity(), EntertainmentActivity.class);
                                                        if (isNetwork == true) {
                                                            startActivity(entertainmentIntent);
                                                        } else {
                                                            Toast.makeText(getActivity().getApplicationContext(), "pleas check your connectivity", Toast.LENGTH_LONG).show();
                                                        }
                                                        break;

                                                    case 6:
                                                        Intent hotelIntent = new Intent(getActivity(), HotelActivity.class);
                                                        if (isNetwork == true) {
                                                            startActivity(hotelIntent);
                                                        } else {
                                                            Toast.makeText(getActivity().getApplicationContext(), "pleas check your connectivity", Toast.LENGTH_LONG).show();
                                                        }
                                                        break;
                                                    case 7:
                                                        Intent religionIntent = new Intent(getActivity(), ReligionActivity.class);
                                                        if (isNetwork == true) {
                                                            startActivity(religionIntent);
                                                        } else {
                                                            Toast.makeText(getActivity().getApplicationContext(), "pleas check your connectivity", Toast.LENGTH_LONG).show();
                                                        }
                                                        break;
                                                    case 8:
                                                        Intent schoolIntent = new Intent(getActivity(), SchoolActivity.class);
                                                        if (isNetwork == true) {
                                                            startActivity(schoolIntent);
                                                        } else {
                                                            Toast.makeText(getActivity().getApplicationContext(), "pleas check your connectivity", Toast.LENGTH_LONG).show();
                                                        }
                                                        break;

                                                }
                                            }
                                        }
        );

        return view;
    }
}