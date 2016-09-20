package com.myfabpics.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.myfabpics.CategoryList;
import com.myfabpics.DataClass.Photo;
import com.myfabpics.OfflineStroage.CircularImageLoader;
import com.myfabpics.OfflineStroage.ImageLoader;
import com.myfabpics.R;

import java.util.ArrayList;


public class PhotoListAdapter extends BaseAdapter implements View.OnClickListener {

    private CategoryList activity;
    private LayoutInflater layoutInflater;
    private ArrayList<Photo> photos;

    public PhotoListAdapter (CategoryList a, LayoutInflater l, ArrayList<Photo> data)
    {
        this.activity = a;
        this.layoutInflater = l;
        this.photos = data;
    }

    @Override
    public int getCount() {
        return this.photos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CategoryList.PhotoHolder holder;
        if (view == null) {
            view = layoutInflater.inflate (R.layout.feed_item, viewGroup, false);
            holder = new CategoryList.PhotoHolder();
            holder.photoTitle = (TextView) view.findViewById(R.id.photo_title);
            holder.photoImage = (ImageView) view.findViewById(R.id.photo_image);
            view.setTag(holder);
        }
        else {
            holder = (CategoryList.PhotoHolder) view.getTag();
        }
        view.setOnClickListener(this);
        Photo photo = photos.get(i);
        holder.photo = photo;
        holder.photoTitle.setText(photo.getTitle());
        Typeface quoteFont = Typeface.createFromAsset(this.activity.getAssets(), "fonts/SymcaPersonalUse.ttf");
        holder.photoTitle.setTypeface(quoteFont);
        ImageLoader imageLoader = new ImageLoader(this.activity);
        imageLoader.displayImage(photo.getImageUrl(), holder.photoImage);
        return view;
    }

    @Override
    public void onClick(View view) {
        CategoryList.PhotoHolder holder = (CategoryList.PhotoHolder) view.getTag();
        //activity.loadUpdateActivity(holder.photo.getId());
    }
}

