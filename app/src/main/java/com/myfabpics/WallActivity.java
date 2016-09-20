package com.myfabpics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Toast;

import com.myfabpics.CommonHelper.CircleNav;
import com.myfabpics.DataClass.Category;
import com.myfabpics.DataClass.NavItem;
import com.myfabpics.DatabaseHandler.DatabaseHelper;
import com.myfabpics.Task.CategoryFetch;
import com.szugyi.circlemenu.view.CircleImageView;
import com.szugyi.circlemenu.view.CircleLayout;
import com.szugyi.circlemenu.view.CircleLayout.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

public class WallActivity extends Activity implements OnItemClickListener {

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

    @Override
    public void onItemClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", view.getId());
        Intent intent = new Intent(WallActivity.this,CategoryList.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void setNavigation() {
        try {
            CircleLayout circleLayout = (CircleLayout) WallActivity.this.findViewById(R.id.circle_layout);
            circleLayout.setOnItemClickListener(this);
            categoryFetch.execute();
        } catch (Exception e) {
            categoryFetch.cancel(true);
        }
    }
}
