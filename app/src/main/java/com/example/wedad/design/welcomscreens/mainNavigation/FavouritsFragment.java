package com.example.wedad.design.welcomscreens.mainNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.PlaceActivity;
import com.example.wedad.design.welcomscreens.SQLiteHelper;
import com.example.wedad.design.welcomscreens.adapter.DividerItemDecoration;
import com.example.wedad.design.welcomscreens.adapter.FavouritAdapter;
import com.example.wedad.design.welcomscreens.adapter.NotifyChange;
import com.example.wedad.design.welcomscreens.beanspkg.Place;

import java.util.List;


public class FavouritsFragment extends Fragment implements NotifyChange {
    RecyclerView recyclerView;
    List<Place> places;
    ImageView img;
    Toolbar toolbar;
    ButtonBarLayout backBtn;
    FavouritAdapter favouritAdapter;
    public static FavouritsFragment newInstance() {
        FavouritsFragment fragment = new FavouritsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onResume() {
        HandleBetween();
        super.onResume();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BottomNavigationView)getActivity().findViewById(R.id.navigation)).setSelectedItemId(R.id.action_home);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //((AppCompatActivity) getActivity()).getSupportActionBar().show();
        View v = inflater.inflate(R.layout.fragment_favourits, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        img=(ImageView)v.findViewById(R.id.img);
        img.setImageResource(R.drawable.favourite_empty_1);
       // ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        backBtn=(ButtonBarLayout)v.findViewById(R.id.back);


    return v;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    //-----------------------------------------------//
    void HandleBetween() {
        SQLiteHelper db = new SQLiteHelper(getContext());
        places=db.getDataFromFavouritDB();
        if (places != null &&places.size()!=0) {
            img.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            img.setVisibility(View.INVISIBLE);
            FavouritAdapter placeAdapter = new FavouritAdapter(getContext(),places);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

            recyclerView.setAdapter(placeAdapter);
            recyclerView.setAdapter(new FavouritAdapter(places, new FavouritAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Place item) {
                    Intent detailIntent = new Intent(recyclerView.getContext(), PlaceActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("choosenPlace", item);
                    detailIntent.putExtras(args);
                    startActivity(detailIntent);
                }
            }, getContext(),this));

        }
        else{
            recyclerView.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);

;

        }
    }
    @Override
    public void onPause() {
        super.onPause();
       // ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void notifyUi() {
        Fragment f=FavouritsFragment.newInstance();
        beginTransaction(f);
    }
    void beginTransaction(Fragment selectedFragment) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commitAllowingStateLoss();

    }
}