package com.pappydevelopers.groupsforwhatsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

final class GridAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;

    public GridAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

        mItems.add(new Item("All",       R.drawable.all));
        mItems.add(new Item("Friendship & Love",   R.drawable.friendship));
        mItems.add(new Item("Educational", R.drawable.education));
        mItems.add(new Item("News",      R.drawable.news));
        mItems.add(new Item("Sports",     R.drawable.sports));
        mItems.add(new Item("Adult 18+",      R.drawable.adult));
        mItems.add(new Item("Health & Fitness",      R.drawable.clubs));
        mItems.add(new Item("Search Groups",      R.drawable.search));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.custom_gridview, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.name, v.findViewById(R.id.name));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.name);

        Item item = getItem(i);

        picture.setImageResource(item.drawableId);
        name.setText(item.name);

        return v;
    }

    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}