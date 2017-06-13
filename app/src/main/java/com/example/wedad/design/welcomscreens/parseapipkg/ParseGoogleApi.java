package com.example.wedad.design.welcomscreens.parseapipkg;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wedad.design.welcomscreens.adapter.LongRunningOperationDelegate;
import com.example.wedad.design.welcomscreens.beanspkg.Place;
import com.example.wedad.design.welcomscreens.volloypkg.AppController;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wedad on 5/25/2017.
 */

public class ParseGoogleApi {
    List<Place> places;
    String url;
    LongRunningOperationDelegate delegate;

    public ParseGoogleApi(LongRunningOperationDelegate delegate) {
        this.delegate = delegate;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void getPlaceUsingForSquer() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                System.out.println("Resopnse" + response.toString());
                places = new ArrayList<Place>();
                try {
                    String content = response.toString();
                    System.out.println("name=" + content);
                    JSONObject jObject = new JSONObject(content);
                    JSONArray result = jObject.getJSONArray("results");
                    System.out.println("Resulte size " + result.length());
                    for (int i = 0; i < result.length(); i++) {
                        Place place = new Place();
                        JSONObject oneObject = result.getJSONObject(i);
                        JSONObject gemotry = oneObject.getJSONObject("geometry");
                        JSONObject location = gemotry.getJSONObject("location");
                        place.setLat(location.getDouble("lat"));
                        place.setLog(location.getDouble("lng"));
                        System.out.println("long" + place.getLat());
                        String icon = oneObject.getString("icon");
                        place.setIcon(icon);
                        String name = oneObject.getString("name");
                        System.out.println("loname=" + name);
                        place.setName(name);
                        try {
                            if (oneObject.has("opening_hours")) {
                                JSONObject opening_hours = oneObject.getJSONObject("opening_hours");
                                if (opening_hours.has("open_now")) {
                                    Boolean opennow = opening_hours.getBoolean("open_now");
                                    place.setOpen(opennow);
                                    System.out.println("opennown=" + opennow);
                                }
                            } else {
                                System.out.println("close");
                            }
                        } catch (JSONException y) {
                            System.out.println("close");
                        }


                        try {
                            double rate = oneObject.getDouble("rating");
                            String rateString = String.valueOf(rate);
                            if (rateString.equals(null)) {

                            } else {
                                System.out.println("rating=" + rate);
                                place.setRating(rateString);
                            }

                        } catch (JSONException ex) {
                            System.out.println("rating empty");
                        }
                        String address = oneObject.getString("vicinity");
                        if (address.equals(null)) {

                        } else {
                            System.out.println("address=" + address);
                            place.setAddress(address);
                        }
                        places.add(place);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    //HandleBetween(places);
                    System.out.println("Placesize" + places.size());
                    //delegate=new FoodActivity();
                    delegate.getListOfPlaces(places);

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
//                hidepDialog();
                System.out.println("Volley error");
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);


    }
}
