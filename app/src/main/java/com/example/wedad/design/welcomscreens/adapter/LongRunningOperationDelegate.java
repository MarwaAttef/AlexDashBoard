package com.example.wedad.design.welcomscreens.adapter;


import com.example.wedad.design.welcomscreens.beanspkg.Place;

import java.util.List;

/**
 * Created by wedad on 5/22/2017.
 */

public interface LongRunningOperationDelegate {
    public void getListOfPlaces(List<Place> placesl);

}
