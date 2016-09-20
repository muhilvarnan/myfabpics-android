package com.myfabpics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.myfabpics.DataClass.Category;
import com.myfabpics.DataClass.Photo;
import com.myfabpics.DatabaseHandler.DatabaseHelper;
import com.myfabpics.Task.CategoryFetch;
import com.myfabpics.Task.CategoryListFetch;
import com.myfabpics.adapter.PhotoListAdapter;
import com.szugyi.circlemenu.view.CircleLayout;

import java.util.ArrayList;

public class CategoryList extends Activity implements CircleLayout.OnItemClickListener  {

    int categoryId;
    private ListView photoList;
    private LayoutInflater layoutInflator;
    private TextView photoTitle;
    CategoryFetch categoryFetch = new CategoryFetch(CategoryList.this);
    CategoryListFetch categoryListFetch = new CategoryListFetch(CategoryList.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Bundle bundle = getIntent().getExtras();
        categoryId = bundle.getInt("categoryId");
        this.categoryId = categoryId;
        this.setNavigation();
        this.imageFetch();
        this.photoList = (ListView) findViewById(R.id.category_image_list);
        this.layoutInflator = LayoutInflater.from(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.nav_float);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircleLayout circleNav = (CircleLayout) CategoryList.this.findViewById(R.id.circle_layout);
                if (circleNav.getVisibility() == View.VISIBLE) {
                    circleNav.setVisibility(View.GONE);
                } else {
                    circleNav.setVisibility(View.VISIBLE);
                }
            }
        });
        TextView categoryTitle = (TextView)findViewById(R.id.category_title);
        Typeface quoteFont   = Typeface.createFromAsset(this.getAssets(), "fonts/AsiyahScript.ttf");
        categoryTitle.setTypeface(quoteFont);
        DatabaseHelper db = new DatabaseHelper(CategoryList.this);
        Category category = db.getCategory(this.categoryId);
        categoryTitle.setText(category.getTitle());
    }

    public int getCategoryId() {
        return this.categoryId;
    }


    public void setQuoteList(ArrayList<Photo> imageList) {
        this.photoList.setAdapter(new PhotoListAdapter(this,this.layoutInflator, imageList));
    }

    public void imageFetch() {
        try {
            categoryListFetch.execute();
        } catch (Exception e) {
            categoryListFetch.cancel(true);
        }
    }

    public static class PhotoHolder {
        public ImageView photoImage;
        public TextView photoTitle;
        public Photo photo;
    }

    public void setNavigation() {
        try {
            CircleLayout circleLayout = (CircleLayout) CategoryList.this.findViewById(R.id.circle_layout);
            circleLayout.setOnItemClickListener(this);
            categoryFetch.execute();
        } catch (Exception e) {
            categoryFetch.cancel(true);
        }
    }

    @Override
    public void onItemClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", view.getId());
        Intent intent = new Intent(CategoryList.this,CategoryList.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void setImageList() {

    }
}
