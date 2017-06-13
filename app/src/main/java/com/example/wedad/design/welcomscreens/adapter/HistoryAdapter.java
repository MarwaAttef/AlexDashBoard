package com.example.wedad.design.welcomscreens.adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;

import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.SQLiteHelper;
import com.example.wedad.design.welcomscreens.beanspkg.Place;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marwa on 6/3/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    static Context context;
    private List<Place> PlacesList;
    int flag=0;
    SQLiteHelper helpher;
    View itemLayoutView;
    NotifyChange notifyChange;
    public interface OnItemClickListener {
        void onItemClick(Place item);
    }

    private HistoryAdapter.OnItemClickListener listener;

    public HistoryAdapter(List<Place> placesList, HistoryAdapter.OnItemClickListener listener, Context c,NotifyChange n) {
        PlacesList = placesList;
        this.listener = listener;
        this.context = c;
        this.notifyChange=n;
    }

    public HistoryAdapter(Context context, List<Place> dbList) {
        this.context = context;
        this.PlacesList = new ArrayList<Place>();
        this.PlacesList = dbList;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.history_item, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView,notifyChange);
        return viewHolder;
    }
    //--------------------------------------------on bind ----------------------------------------//

    @Override
    public void onBindViewHolder(final HistoryAdapter.ViewHolder holder, final int position) {
        final Place place = PlacesList.get(position);

        holder.bind(PlacesList.get(position), listener);
        holder.name.setText(PlacesList.get(position).getName());
        //  Toast.makeText(HistoryAdapter.context, PlacesList.get(position).getName() + position, Toast.LENGTH_LONG).show();
        holder.email.setText(PlacesList.get(position).getDate());
        helpher = new SQLiteHelper(  itemLayoutView.getContext());
        Boolean vCheck = helpher.checkPlaceExistence(place.getName());
        if (vCheck  == true) {
           // flag = 0;
            holder.favourite.setImageResource(R.drawable.group3157);
            PlacesList.get(position).setCheckFlag(0);
        } else {
            holder.favourite.setImageResource(R.drawable.group3132);
            PlacesList.get(position).setCheckFlag(1);
           // flag = 1;
        }
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
///----------------------------------addTo Favourite-----------------------------------------///
                System.out.println("hi click");
                if(PlacesList.get(position).getCheckFlag()==0) {
                    //flag = 1;
                    PlacesList.get(position).setCheckFlag(1);
                    holder.favourite.setImageResource(R.drawable.group3132);
                    Boolean   vCheck = helpher.checkPlaceExistence(place.getName());
                    if (vCheck == true) {
                        notifyDataSetChanged();
                        helpher.insertIntoFavouritDB(place);
                        ///saveindatabase
                    }
                }
                else
                {
                    //flag=0;
                    PlacesList.get(position).setCheckFlag(0);
                    holder.favourite.setImageResource(R.drawable.group3157);
                    helpher = new SQLiteHelper(v.getContext());
                    helpher.deleteARowFavourit(place.getName());
                    notifyDataSetChanged();

                }
            }
        });

        Picasso.with(context)
                .load(PlacesList.get(position).getIcon())
                .into(holder.icon);
        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
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
                                db.deleteARowHistory(PlacesList.get(position).getDate());
                                PlacesList.remove(position);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
                                if(PlacesList.size()==0)
                                {


                                    holder.notifyChange.notifyUi();

                                }
                               // Toast.makeText(HistoryAdapter.context, "you have Long clicked Row " + position, Toast.LENGTH_LONG).show();
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
        });

    }

    //-----------------------------------get item count ---------------------------------------------//
    @Override
    public int getItemCount() {
        return PlacesList.size();
    }

    //-----------------------------------------class viewholder---------------------------------------------------------//
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardview;
        public TextView name, email;
        public ImageView icon;
        ImageButton favourite;
        NotifyChange notifyChange;
        public ViewHolder(View itemLayoutView, NotifyChange notifyChange) {
            super(itemLayoutView);
            this.notifyChange=notifyChange;
            name = (TextView) itemLayoutView.findViewById(R.id.name);
            email = (TextView) itemLayoutView.findViewById(R.id.popularname);
            cardview = (CardView) itemLayoutView.findViewById(R.id.cardViewId);
            icon =(ImageView) itemLayoutView.findViewById(R.id.icon);
            favourite=(ImageButton)itemLayoutView.findViewById(R.id.imageButton1);

            int screen_height = 0, screen_width = 0;
            WindowManager wm;
            DisplayMetrics displaymetrics;

            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            displaymetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(displaymetrics);
            screen_height = displaymetrics.heightPixels;
            screen_width = displaymetrics.widthPixels;
            icon.getLayoutParams().width = (int) (0.15 * screen_width);
            icon.getLayoutParams().height = (int) (0.15 * screen_width);

        }
        public void bind(final Place item, final HistoryAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
