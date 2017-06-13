package com.example.wedad.design.welcomscreens.homepkg;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.PlaceActivity;
import com.example.wedad.design.welcomscreens.homepkg.weatherapi.CityPreference;
import com.example.wedad.design.welcomscreens.homepkg.weatherapi.RemoteFetch;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class WeatherFragment extends Fragment {
    TextView timeDate;
    View view;
    Handler handler;


    ImageButton search;
    Typeface weatherFont;
    TextView weatherIcon;
    TextView cityField;
    TextView humidity;
    TextView pressure;
    TextView currentTemperatureField;
    TextView detailsField;
    SupportPlaceAutocompleteFragment autocompleteFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        updateWeatherData(new CityPreference(getActivity()).getCity());

    }

    //-------------------------------------------------------------------------------------------------//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weather, container, false);

        timeDate = (TextView) view.findViewById(R.id.timedate);
        weatherIcon = (TextView) view.findViewById(R.id.weather_icon);
        cityField = (TextView) view.findViewById(R.id.city_field);
        humidity = (TextView) view.findViewById(R.id.humidity);
        pressure = (TextView) view.findViewById(R.id.pressure);
        currentTemperatureField = (TextView) view.findViewById(R.id.current_temperature_field);
        ////----------------weather icon margin-------------------------------------------------//
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)weatherIcon.getLayoutParams();
        params.setMargins(0,0,0,2);
        weatherIcon.setLayoutParams(params);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/HelveticaNeue-Regular.ttf");
        //currentTemperatureField.setTypeface(font);
        //----------------------------------------temperature margin---------------------------------------//
        LinearLayout.LayoutParams paramsTemp = (LinearLayout.LayoutParams)currentTemperatureField.getLayoutParams();
        paramsTemp.setMargins(0,35,0,0);
        currentTemperatureField.setLayoutParams(paramsTemp);

        //------------------------------------------------------------------------------------------------//


        detailsField = (TextView) view.findViewById(R.id.details_field);
        weatherIcon.setTypeface(weatherFont);

        autocompleteFragment  = (SupportPlaceAutocompleteFragment)getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
       ((EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setHint("");
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_COUNTRY)
                .setCountry("EG")
                .build();
        autocompleteFragment.setFilter(autocompleteFilter);
        ImageView searchIcon = ((ImageView)((LinearLayout) autocompleteFragment.getView()).getChildAt(0));

          // Set the desired icon
        searchIcon.setImageDrawable(getResources().getDrawable(R.drawable.search3));
        //------------------------saerch icon margin -------------------------------///
        LinearLayout.LayoutParams paramSearch = (LinearLayout.LayoutParams)searchIcon.getLayoutParams();
        paramSearch.setMargins(90,0,0,0);
        searchIcon.setLayoutParams(paramSearch);
        //-------------------------------------------------------------------------///
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                System.out.println("Hello Click");
                // TODO: Get info about the selected place.
//                autocompleteFragment.setText("");
//                v.setVisibility(View.GONE);
                System.out.println("Place: " + place.getName());

                System.out.println("atribution" + place.getAttributions());
                System.out.println("phonenumber" + place.getPhoneNumber());
                System.out.println("placetype" + place.getPlaceTypes());
                System.out.println("placerating" + place.getRating());
                System.out.println("websiteurl" + place.getWebsiteUri());
                Geocoder coder = new Geocoder(getContext());
                List<Address> address;
                LatLng p1 = null;
                com.example.wedad.design.welcomscreens.beanspkg.Place p=new com.example.wedad.design.welcomscreens.beanspkg.Place();
                p.setName((String) place.getName());

                 try {
                     // May throw an IOException
                     address = coder.getFromLocationName((String) place.getAddress(), 5);
                     if (address != null) {

                        try {
                            Address location = address.get(0);
                            location.getLatitude();
                            location.getLongitude();
                            System.out.println("location" + location.getLatitude());
                            p.setLog(location.getLongitude());
                            p.setLat(location.getLatitude());
                            p.setAddress((String) place.getAddress());
                            if (place.getWebsiteUri() != null) {

                                String stringUri = place.getWebsiteUri().toString();
                                p.setUrl(stringUri);
                            }
                            String rate = Float.toString(place.getRating());
                            p.setRating(rate);
                            String phone = (String) (place.getPhoneNumber());
                            p.setPhone(phone.trim());
                            System.out.println("location" + location.getLongitude());
                            p1 = new LatLng(location.getLatitude(), location.getLongitude());
                            Intent detailIntent = new Intent(getContext(), PlaceActivity.class);
                            Bundle args = new Bundle();
                            args.putSerializable("choosenPlace", p);
                            detailIntent.putExtras(args);
                            startActivity(detailIntent);
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getActivity().getApplicationContext(),"This Place Not Found",Toast.LENGTH_LONG).show();
                        }

                     } else
                     {
                         Toast.makeText(getActivity().getApplicationContext(),"This Place Not Found",Toast.LENGTH_LONG).show();
                     }
                } catch (IOException ex) {

                    ex.printStackTrace();
                }
            }

            @Override
            public void onError(Status status) {

            }




        });
        return view;
    }

    //------------------------------------WeatherFragment------------------------------------------------------//
    public WeatherFragment() {
        handler = new Handler();
    }

    //------------------------------------updateWeatherData------------------------------------------------------//
    private void updateWeatherData(final String city) {
        new Thread() {
            public void run() {
                final JSONObject json = RemoteFetch.getJSON(getActivity(), city);
                if (json == null) {
                    handler.post(new Runnable() {
                        public void run() {
                            renderWeather(json);

                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }


    //-------------------------------------Render Weather-----------------------------------------------//
    private void renderWeather(JSONObject json) {
        String humidityData = "";
        String pressureData = "";
        String detailsData = "";
        String currentTemperatureData = "";
        String updatedOn = "";
        int actualIdData = 0;
        Long sunriseData = 0L;
        Long sunsetData = 0L;
        try {
            cityField.setText("Alexandria, EG");
            if (json != null) {

                JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                JSONObject main = json.getJSONObject("main");
                humidityData = main.getString("humidity") + "%";
                pressureData = main.getString("pressure") + " hPa";
                detailsData = details.getString("description").toUpperCase(Locale.US);
                currentTemperatureData = String.format("%.2f", main.getDouble("temp")) + "℃";
                DateFormat df = DateFormat.getDateTimeInstance();
                updatedOn = df.format(new Date(json.getLong("dt") * 1000));
                actualIdData = details.getInt("id");
                sunriseData = json.getJSONObject("sys").getLong("sunrise") * 1000;
                sunsetData = json.getJSONObject("sys").getLong("sunset") * 1000;

                //-----------------------------------------set in shared preference---------------------------------------//
                SharedPreferences.Editor editor = timeDate.getContext().getSharedPreferences("weatherData", MODE_PRIVATE).edit();
                editor.putString("humidityData", humidityData);
                editor.putString("pressureData", pressureData);
                editor.putString("detailsData", detailsData);
                editor.putString("currentTemperatureData", currentTemperatureData);
                editor.putString("updatedOn", updatedOn);
                editor.putInt("actualIdData", actualIdData);
                editor.putLong("sunriseData", sunriseData);
                editor.putLong("sunsetData", sunsetData);
                editor.commit();
            }
            //--------------------------------------get from shared preference-----------------------------------------------//
            else {

                SharedPreferences prefs = timeDate.getContext().getSharedPreferences("weatherData", MODE_PRIVATE);
                humidityData = prefs.getString("humidityData", "50%");//"No name defined" is the default value.
                pressureData = prefs.getString("pressureData", "1017hPa");//"No name defined" is the default value.
                detailsData = prefs.getString("detailsData", "CLEAR SKY");//"No name defined" is the default value.
                currentTemperatureData = prefs.getString("currentTemperatureData", "26.00 ℃");//"No name defined" is the default value.
                updatedOn = prefs.getString("updatedOn", "Jun 1,2017 4:00:00PM");//"No name defined" is the default value.
                actualIdData = prefs.getInt("actualIdData", 800); //0 is the default value.
                sunriseData = prefs.getLong("sunriseData", 1496285795000L); //0 is the default value.
                sunsetData = prefs.getLong("sunsetData", 1496336392000L); //0 is the default value.
            }
            //---------------------------------------------------------------------------------------------------------------//

            humidity.setText(humidityData);
            pressure.setText(pressureData);
            detailsField.setText(detailsData);
            currentTemperatureField.setText(currentTemperatureData);
            timeDate.setText(updatedOn);
            setWeatherIcon(actualIdData, sunriseData, sunsetData);

        } catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }

    }

    //---------------------------------------setWeatherIcon---------------------------------------------//
    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2:
                    icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = getActivity().getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherIcon.setText(icon);
    }

}