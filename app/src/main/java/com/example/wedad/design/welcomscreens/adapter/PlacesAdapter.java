package com.example.wedad.design.welcomscreens.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.SQLiteHelper;
import com.example.wedad.design.welcomscreens.beanspkg.Place;
import com.example.wedad.design.welcomscreens.singletonepkg.CoordinatesSingleTone;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by wedad on 5/23/2017.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.MyViewHolder> {
    double currentLatitude;
    double currentLongitude;

    public interface OnItemClickListener {
        void onItemClick(Place item);
    }

    int flag = 0;
    private List<Place> PlacesList;
    private OnItemClickListener listener;
    Context context;
    View itemView;
    SQLiteHelper helpher;

    public PlacesAdapter(List<Place> placesList, OnItemClickListener listener, Context c) {
        PlacesList = placesList;
        this.listener = listener;
        this.context = c;
    }

    public PlacesAdapter(List<Place> placesList, Context c) {
        PlacesList = placesList;
        this.context = c;
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.bind(PlacesList.get(position), listener);

        final Place place = PlacesList.get(position);

        holder.txtName.setText(place.getName());
        holder.txtPopularName.setText(place.getPopularName());
        holder.Rate.setText(place.getRating());
        holder.txtDistance.setText(place.getAddress());
        WindowManager wm;
        DisplayMetrics displaymetrics;
        int screen_height=0,screen_width=0;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);
        screen_height = displaymetrics.heightPixels;
        screen_width = displaymetrics.widthPixels;
        try {
            holder.review.setText(place.getTip().getText());

          holder.cardview.getLayoutParams().height = (int) (0.35 * screen_height);
            //holder.userName.setText(place.getTip().getUser().getFirstName());
        }
        catch (NullPointerException e)
        {
            holder.cardview.getLayoutParams().height = (int) (0.3 * screen_height);
            System.out.println("No review");
        }

        if (place.getRating() == null) {

            holder.rateLayout.setBackgroundColor(Color.rgb(255, 255, 255));
        }


        Picasso.with(context)
                .load(place.getIcon())
                .into(holder.icon);
        holder.icon.setColorFilter(context.getResources().getColor(R.color.weather));
      //  holder.icon.setBackgroundColor(Color.rgb(3, 114, 255));

        holder.favourite.setColorFilter(context.getResources().getColor(R.color.fav_gray));
//        holder.direction.setColorFilter(context.getResources().getColor(R.color.colorPrimary));


        helpher = new SQLiteHelper(itemView.getContext());
        Boolean vCheck = helpher.checkPlaceExistence(place.getName());
        if (vCheck == true) {
            flag = 0;
            //holder.favourite.setImageResource(R.drawable.ic_favorite);
           holder.favourite.setColorFilter(context.getResources().getColor(R.color.fav_gray));

        } else {
            //holder.favourite.setImageResource(R.drawable.ic_favorite_1);
            holder.favourite.setColorFilter(context.getResources().getColor(R.color.fav_red));
            flag = 1;
        }
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (flag == 0) {
                     //holder.favourite.setImageResource(R.drawable.ic_favorite_1);
                    holder.favourite.setColorFilter(context.getResources().getColor(R.color.fav_red));
                    flag = 1;
                    helpher = new SQLiteHelper(v.getContext());
                    Boolean check = helpher.checkPlaceExistence(place.getName());
                    if (check == true) {

                        helpher.insertIntoFavouritDB(place);
                        ///saveindatabase
                    }
                } else {
                    holder.favourite.setColorFilter(context.getResources().getColor(R.color.fav_gray));
                   // holder.favourite.setImageResource(R.drawable.ic_favorite);
                    helpher = new SQLiteHelper(v.getContext());
                    helpher.deleteARowFavourit(place.getName());

                    flag = 0;
                    //remove
                }
                //actions
            }

        });
        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        holder.direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//--------------------------------------------------------------------------------------------//
                double destinationLatitude = place.getLat();
                double destinationLongitude = place.getLog();
                System.out.println(":D'" + destinationLongitude + destinationLatitude);
                String url;
                currentLatitude = CoordinatesSingleTone.lat;
                currentLongitude = CoordinatesSingleTone.log;
                System.out.println("current" + currentLatitude);
                System.out.println("current" + currentLongitude);
                url = "http://maps.google.com/maps?saddr=" + currentLatitude + "," + currentLongitude + "&daddr=" + destinationLatitude + "," + destinationLongitude;
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return PlacesList.size();
    }


//------------------------------------class view holder -----------------------------------------------------//

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtPopularName;
        TextView txtDistance;
        TextView Rate;
        TextView review;
        TextView userName;
        CardView cardview;
        ImageView icon;
        ImageButton favourite;
        ImageButton direction;
        LinearLayout rateLayout;

        public MyViewHolder(View view) {
            super(view);
            review=(TextView)view.findViewById(R.id.review);
            userName=(TextView)view.findViewById(R.id.username);
            txtName = (TextView) view.findViewById(R.id.name);
            txtPopularName = (TextView) view.findViewById(R.id.popularname);
            txtDistance = (TextView) view.findViewById(R.id.diatance);
            Rate = (TextView) view.findViewById(R.id.Rate);
            icon = (ImageView) view.findViewById(R.id.icon);
            cardview = (CardView) view.findViewById(R.id.cardViewId);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            favourite = (ImageButton) view.findViewById(R.id.imageButton1);
            direction = (ImageButton) view.findViewById(R.id.imageButton2);
            rateLayout=(LinearLayout)view.findViewById(R.id.ratelayout);
            int screen_height = 0, screen_width = 0;
            WindowManager wm;
            DisplayMetrics displaymetrics;

            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            displaymetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(displaymetrics);
            screen_height = displaymetrics.heightPixels;
            screen_width = displaymetrics.widthPixels;
            icon.getLayoutParams().width = (int) (0.2 * screen_width);
            icon.getLayoutParams().height = (int) (0.2* screen_width);

        }

        public void bind(final Place item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


}
