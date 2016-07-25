package com.myfabpics;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.myfabpics.CommonHelper.CircleNav;
import com.myfabpics.CommonHelper.RemoteCategory;
import com.myfabpics.DataClass.NavItem;
import com.myfabpics.Task.CategoryFetch;
import com.szugyi.circlemenu.view.CircleImageView;
import com.szugyi.circlemenu.view.CircleLayout;

import java.util.ArrayList;
import java.util.List;

public class WallActivity extends Activity {

    CategoryFetch categoryFetch = new CategoryFetch(WallActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_activity);
        this.setNavigation();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.nav_float);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircleLayout circleNav = (CircleLayout) WallActivity.this.findViewById(R.id.circle_layout);
                if (circleNav.getVisibility() == View.VISIBLE) {
                    circleNav.setVisibility(View.GONE);
                } else {
                    circleNav.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void setNavigation() {
        try {
            categoryFetch.execute();
        } catch (Exception e) {
            categoryFetch.cancel(true);
        }
        CircleLayout circleLayout = (CircleLayout) WallActivity.this.findViewById(R.id.circle_layout);
        List<NavItem> navItemList = new ArrayList<NavItem>();
        navItemList.add(new NavItem(1, "home", R.drawable.ic_home));
        navItemList.add(new NavItem(2, "flora", R.drawable.ic_flora));
        navItemList.add(new NavItem(3, "fauna", R.drawable.ic_fauna));
        navItemList.add(new NavItem(4, "fantansy", R.drawable.ic_fantasy));
        CircleNav circleNav = new CircleNav(this, circleLayout);
        circleNav.addNavigationItem(navItemList);
    }

}
