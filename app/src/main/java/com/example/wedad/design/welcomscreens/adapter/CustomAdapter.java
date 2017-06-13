package com.example.wedad.design.welcomscreens.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.wedad.design.R;
import com.example.wedad.design.welcomscreens.beanspkg.Place;

import java.util.List;

/**
 * Created by wedad on 5/7/2017.
 */

public class CustomAdapter extends ArrayAdapter<Place> implements View.OnClickListener {
    private List<Place> dataSet;
    Context mContext;
    View v;


    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtPopularName;
        TextView txtDistance;
        TextView Rate;

    }

    public CustomAdapter(List<Place> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Place place = (Place) object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Place place = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtPopularName = (TextView) convertView.findViewById(R.id.popularname);
            viewHolder.txtDistance = (TextView) convertView.findViewById(R.id.diatance);
            viewHolder.Rate = (TextView) convertView.findViewById(R.id.Rate);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        lastPosition = position;

        viewHolder.txtName.setText(place.getName());
        viewHolder.txtPopularName.setText(place.getPopularName());
        System.out.println(place.getDistance());
        viewHolder.txtDistance.setText((place.getDistance()));
        System.out.println(place.getRating());
        viewHolder.Rate.setText((place.getRating()));

        // Return the completed view to render on screen
        return convertView;
    }

}
