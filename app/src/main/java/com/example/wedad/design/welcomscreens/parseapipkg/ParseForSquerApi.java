package com.example.wedad.design.welcomscreens.parseapipkg;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wedad.design.welcomscreens.adapter.LongRunningOperationDelegate;
import com.example.wedad.design.welcomscreens.beanspkg.Place;
import com.example.wedad.design.welcomscreens.beanspkg.Tips;
import com.example.wedad.design.welcomscreens.beanspkg.User;
import com.example.wedad.design.welcomscreens.volloypkg.AppController;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wedad on 5/20/2017.
 */

public class ParseForSquerApi {

    List<Place> places;
    String url;
    LongRunningOperationDelegate delegate;

    public ParseForSquerApi(LongRunningOperationDelegate delegate) {
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
                Tips tip;
                System.out.println("Resopnse" + response.toString());
                places = new ArrayList<Place>();
                try {
                    // Parsing json object response
                    // response will be a json object
                    JSONArray venues = response.getJSONObject("response")
                            .getJSONArray("groups")
                            .getJSONObject(0)
                            .getJSONArray("items");
                    System.out.println("place " + venues);
                    for (int i = 0; i < venues.length(); i++) {
                        Place place = new Place();
                        JSONObject ve = venues.getJSONObject(i).getJSONObject("venue");

                        // System.out.println("venuens object"+venues.getJSONObject(i).getJSONArray("tips"));
                        System.out.println("Place name :" + ve.get("name"));
                        place.setName(ve.getString("name"));
                        System.out.println("place name    " + place.getName());
                        if (ve.has("contact")) {
                            JSONObject contact = ve.getJSONObject("contact");

                            if (contact.has("phone")) {
                                place.setPhone(contact.getString("phone"));
                                System.out.println("Place tele :" + contact.get("phone"));
                            } else {
                                System.out.println("No phone");
                                //place.setPhone("No phone");
                            }
                            if (contact.has("formattedPhone")) {
                                System.out.println("Place formatted phone :" + contact.get("formattedPhone"));
                                place.setFormattedPhone(contact.getString("formattedPhone"));
                            } else {
                                System.out.println("No formatted phone");
                                //  place.setFormattedPhone("No formatted phone");
                            }
                            if (contact.has("facebookUsername")) {
                                System.out.println("facebookUsername :" + contact.get("facebookUsername"));
                                place.setFaceBookUserName(contact.getString("facebookUsername"));
                            } else {
                                System.out.println("No facebookUsername");

                                place.setFaceBookUserName("No facebookUsername");
                            }
                        } else {
                            System.out.println("has no contact");

                        }
                        if (ve.has("location")) {
                            JSONObject location = ve.getJSONObject("location");
                            if (location.has("address")) {

                                System.out.println("adresss = " + location.get("address"));
                                place.setAddress(location.getString("address"));
                            } else {

                                System.out.println("No address");
                                //    place.setAddress("No address");
                            }
                            if (location.has("crossStreet")) {
                                System.out.println("Cross street" + location.get("crossStreet"));
                                place.setCrossStreet(location.getString("crossStreet"));

                            }
                            System.out.println("lat" + location.get("lat"));
                            place.setLat(location.getDouble("lat"));
                            System.out.println("lng" + location.get("lng"));
                            place.setLog(location.getDouble("lng"));
                        }
                        if (ve.has("distance")) {

                            System.out.println("Distance = " + ve.getString("distance"));
                            place.setDistance(ve.getString("distance"));

                        }
                        if (ve.has("stats")) {
                            JSONObject stats = ve.getJSONObject("stats");
                            if (stats.has("checkinsCount")) {
                                System.out.println("Number of checkins people " + stats.get("checkinsCount"));
                                place.setCheckInCount(stats.getString("checkinsCount"));
                            } else {
                                System.out.println("No checkins people");
                                //place.setCheckInCount("No checkins people");
                            }

                        }
                        if (ve.has("categories")) {
                            JSONArray categories = ve.getJSONArray("categories");
                            for (int j = 0; j < categories.length(); j++) {
                                JSONObject firstCategory = categories.getJSONObject(j);
                                place.setDomainName(firstCategory.getString("name"));
                                System.out.println("name" + firstCategory.get("name"));
                                place.setPopularName(firstCategory.getString("pluralName"));
                                System.out.println("popular" + firstCategory.get("pluralName"));
                                if (firstCategory.has("icon")) {
                                    JSONObject icon = firstCategory.getJSONObject("icon");
                                    String image = icon.getString("prefix") + "64" + icon.getString("suffix");
                                    System.out.println(icon.getString("prefix"));
                                    System.out.println(icon.getString("suffix"));
                                    place.setIcon(image);
                                    System.out.println(image);

                                }
                            }

                        }
                        try {
                            if (venues.getJSONObject(i).getJSONArray("tips") != null) {
                                System.out.println("venuens object" + venues.getJSONObject(i).getJSONArray("tips"));
                                JSONArray tips = venues.getJSONObject(i).getJSONArray("tips");
                                for (int k = 0; k < tips.length(); k++) {
                                    JSONObject tipeitem = tips.getJSONObject(k);
                                    tip = new Tips();

                                    if (tipeitem.has("text")) {
                                        tip.setText(tipeitem.getString("text"));
                                        System.out.println("review" + tipeitem.get("text"));
                                    } else {
                                        //tip.setText("no text");
                                        System.out.println("No review");
                                    }
                                    if (tipeitem.has("likes")) {
                                        tip.setNumberOfLikes(tipeitem.getString("likes"));
                                        System.out.println("Number of likes on this review" + tipeitem.get("likes"));
                                        String li = tipeitem.getString("likes");

                                    } else {
                                        //tip.setNumberOfLikes("no likes");
                                        System.out.println("No likes on this review");
                                    }
                                    if (tipeitem.has("canonicalUrl")) {
                                        tip.setUrl(tipeitem.getString("canonicalUrl"));
                                        System.out.println("canonicalUrl=" + tipeitem.get("canonicalUrl"));

                                    } else {
                                        //tip.setUrl("no url");
                                        System.out.println("No canonicalUrl");
                                    }
                                    if (tipeitem.has("user")) {

                                        JSONObject user = tipeitem.getJSONObject("user");
                                        User users = new User();
                                        if (user.has("firstName")) {
                                            users.setFirstName(user.getString("firstName"));

                                            System.out.println("Firstname" + user.get("firstName"));
                                        } else {
                                            //users.setFirstName("no firstname");
                                            System.out.println("No Firstname");
                                        }
                                        if (user.has("lastName")) {
                                            users.setLastName(user.getString("lastName"));
                                            System.out.println("Last Name :" + user.get("lastName"));
                                        } else {
                                            System.out.println("No Last Name");
                                        }
                                    }

                                    place.setTip(tip);
                                }

                            }
                            if (ve.has("url")) {
                                place.setUrl(ve.getString("url"));
                                System.out.println("Url" + ve.get("url"));

                            }
                            if (ve.has("rating")) {
                                place.setRating(ve.getString("rating"));
                                System.out.println("Rate" + ve.getString("rating"));
                            }
                            if (ve.has("ratingSignals")) {
                                place.setRatingSignals(ve.getString("ratingSignals"));
                                System.out.println("ratingSignals" + ve.get("ratingSignals"));
                            }
                            if (ve.has("menu")) {
                                JSONObject menu = ve.getJSONObject("menu");
                                if (ve.has("mobileUrl")) {
                                    place.setMobileUrl(ve.getString("mobileUrl"));
                                    System.out.println("mobile url=" + ve.get("mobileUrl"));
                                } else {
                                    System.out.println("no mobileurl");
                                }
                            }
                            if (ve.has("hours")) {
                                JSONObject hours = ve.getJSONObject("hours");
                                if (hours.has("isOpen")) {
                                    place.setOpen(hours.getBoolean("isOpen"));
                                    System.out.println("open or not" + hours.get("isOpen"));

                                } else {
                                    System.out.println("No is open");
                                }
                            }
                            places.add(place);

                          } catch (JSONException e1) {
                            System.out.println("No value for tips");
                            places.add(place);
                        }

                        System.out.println("Placesize" + places.size());
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
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
