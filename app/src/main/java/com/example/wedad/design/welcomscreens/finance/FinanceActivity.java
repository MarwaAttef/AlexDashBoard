package com.example.wedad.design.welcomscreens.finance;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.singletonepkg.CoordinatesSingleTone;

public class FinanceActivity extends AppCompatActivity {
    double lat, log;
    private FragmentTabHost mTabHost;

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //<< this
        getSupportActionBar().setTitle("Finance");  // provide compatibility to all the versions
        lat = CoordinatesSingleTone.lat;
        log = CoordinatesSingleTone.log;
        System.out.println("lat:-" + lat);
        //----------------------------------------------------------------------//
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabFrameLayout);
        mTabHost.setBackgroundColor(Color.rgb(238,238,238));

        //////-------------------------------------- add to tab--------------------------------------///
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator(getTabIndicator(mTabHost.getContext(), "Bank")),
                BankFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator(getTabIndicator(mTabHost.getContext(), "ATM")),
                ATMFragment.class, null);
    }
    private View getTabIndicator(Context context, String title) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);

        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(title);
        return view;
    }
}
