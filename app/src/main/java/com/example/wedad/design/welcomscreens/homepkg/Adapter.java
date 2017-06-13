package com.example.wedad.design.welcomscreens.homepkg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.example.wedad.design.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marwa on 5/30/2017.
 */

public class Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Item> items = new ArrayList<Item>();

    public Adapter(Context context) {
        inflater = LayoutInflater.from(context);
        items.add(new Item("Image 1", R.drawable.food));
        items.add(new Item("Image 2", R.drawable.emergency));
        items.add(new Item("Image 3", R.drawable.attractions));
        items.add(new Item("Image 4", R.drawable.finance));
        items.add(new Item("Image 5", R.drawable.health));
        items.add(new Item("Image 6", R.drawable.entertainment));
        items.add(new Item("Image 7", R.drawable.hotels));
        items.add(new Item("Image 8", R.drawable.religious));
        items.add(new Item("Image 9", R.drawable.school));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).drawableId;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        SquareImageView picture;
        if (v == null) {
            v = inflater.inflate(R.layout.gridview_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setBottom(23);
        }
        picture = (SquareImageView) v.getTag(R.id.picture);
        Item item = (Item) getItem(i);
        picture.setImageResource(item.drawableId);
        return v;
    }

    private class Item {

        final int drawableId;

        Item(String name, int drawableId) {
            this.drawableId = drawableId;
        }
    }
}



