package com.example.wedad.design.welcomscreens.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.SQLiteHelper;
import com.example.wedad.design.welcomscreens.beanspkg.Place;
import com.example.wedad.design.welcomscreens.singletonepkg.CoordinatesSingleTone;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marwa on 6/3/2017.
 */

public class FavouritAdapter extends RecyclerView.Adapter<FavouritAdapter.ViewHolder>  {
    static Context context;
    private List<Place> PlacesList;
    double currentLatitude;
    double currentLongitude;
    NotifyChange notifyChange;


    public interface OnItemClickListener {
        void onItemClick(Place item);
    }
    private FavouritAdapter.OnItemClickListener listener;

    public FavouritAdapter(List<Place> placesList, FavouritAdapter.OnItemClickListener listener, Context c,NotifyChange n) {
        PlacesList = placesList;
        this.listener = listener;
        this.context = c;
        this.notifyChange=n;
    }


    public FavouritAdapter(Context context, List<Place> dbList ){
        this.context = context;
        this.PlacesList = new ArrayList<Place>();
        this.PlacesList = dbList;

    }

    @Override
    public FavouritAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.favourite_item, null);

        // create ViewHolder

        FavouritAdapter.ViewHolder viewHolder = new FavouritAdapter.ViewHolder(itemLayoutView,notifyChange);
        return viewHolder;
    }
    //--------------------------------------------on bind ----------------------------------------//



    @Override
    public void onBindViewHolder(final FavouritAdapter.ViewHolder holder, final int position) {

        System.out.println(holder);
        //   holder.cardview.getLayoutParams().height = (int) (0.2 * screen_height);
        holder.bind(PlacesList.get(position), listener);
//        holder.direction.setColorFilter(context.getResources().getColor(R.color.colorPrimary));
        holder.direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//--------------------------------------------------------------------------------------------//
                double destinationLatitude = PlacesList.get(position).getLat();
                double destinationLongitude = PlacesList.get(position).getLog();
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




        holder.name.setText(PlacesList.get(position).getName());
//        Toast.makeText(FavouritAdapter.context,PlacesList.get(position).getName() +position, Toast.LENGTH_LONG).show();
        holder.email.setText(PlacesList.get(position).getPopularName());
        holder.Rate.setText(PlacesList.get(position).getRating());
        if (PlacesList.get(position).getRating() == null) {
           // holder.Rate.setBackgroundColor(Color.rgb(3,114,255));
            holder.ratlinearlayout.setBackgroundColor(Color.rgb(255, 255, 255));

        }
        Picasso.with(context)
                .load(PlacesList.get(position).getIcon())
                .into(holder.icon);
        holder.cardview.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Delete place");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to delete this place")
                        .setCancelable(false)
                        .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SQLiteHelper db = new SQLiteHelper(view.getContext());
                                db.deleteARowFavourit(PlacesList.get(position).getName());
                                PlacesList.remove(position);
                                notifyDataSetChanged();
                                notifyItemRemoved(position);
                                if(PlacesList.size()==0)
                                {


                                       holder.notifyChange.notifyUi();

                                }
                                //Toast.makeText(FavouritAdapter.context, "you have Long clicked Row " + position, Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();


                return true;// returning true instead of false, works for me
            }
        }

        );



    }



    //-----------------------------------get item count ---------------------------------------------//
    @Override
    public int getItemCount() {
        return PlacesList.size();
    }
    //-----------------------------------------class viewholder---------------------------------------------------------//
    public static class ViewHolder extends RecyclerView.ViewHolder  {
        NotifyChange notifyChange;



        CardView cardview;
        RelativeLayout relativeLayout;
        ImageView icon;
        TextView Rate;
        ImageButton direction;
        LinearLayout ratlinearlayout;
        public TextView name,email;
        public ViewHolder(View itemLayoutView,NotifyChange notifyChange) {
            super(itemLayoutView);
            this.notifyChange = notifyChange;
            name = (TextView) itemLayoutView.findViewById(R.id.name);
            email = (TextView)itemLayoutView.findViewById(R.id.popularname);
            cardview = (CardView) itemLayoutView.findViewById(R.id.fCardViewId);
            icon =(ImageView) itemLayoutView.findViewById(R.id.icon);
            Rate = (TextView) itemLayoutView.findViewById(R.id.Rate);
            relativeLayout=(RelativeLayout)itemLayoutView.findViewById(R.id.Relative);
            direction=(ImageButton) itemLayoutView.findViewById(R.id.imageButton2);
            ratlinearlayout=(LinearLayout)itemLayoutView.findViewById(R.id.rate2layout);
            int screen_height = 0, screen_width = 0;
            WindowManager wm;
            DisplayMetrics displaymetrics;

            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            displaymetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(displaymetrics);
            screen_height = displaymetrics.heightPixels;
            screen_width = displaymetrics.widthPixels;
//icon.setMinimumHeight((int) (0.2*screen_width));
//icon.setMinimumWidth((int) (0.2*screen_width));
            icon.getLayoutParams().width = (int) (0.2 * screen_width);
            icon.getLayoutParams().height = (int) (0.2 * screen_width);

            cardview.setMinimumHeight((int) (0.2*screen_height));
            relativeLayout.setMinimumHeight((int) (0.2*screen_height));
            System.out.println("layout"+cardview.getLayoutParams());

        }


        public void bind(final Place item, final FavouritAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
