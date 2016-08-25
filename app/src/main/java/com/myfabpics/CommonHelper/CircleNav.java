package com.myfabpics.CommonHelper;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;

import com.myfabpics.DataClass.NavItem;
import com.myfabpics.OfflineStroage.CircularImageLoader;
import com.myfabpics.R;
import com.szugyi.circlemenu.view.CircleImageView;
import com.szugyi.circlemenu.view.CircleLayout;

import java.util.List;

/**
 * Created by root on 24/7/16.
 */
public class CircleNav {

    private Activity activity;
    private CircleLayout circleLayout;
    private CircularImageLoader circularImageLoader;

    public CircleNav(Activity activity, CircleLayout circleLayout) {
        this.activity = activity;
        this.circleLayout = circleLayout;
        this.circularImageLoader = new CircularImageLoader(activity);
    }

    public void addNavigationItem(List<NavItem> navItemList) {
        for (NavItem navitem: navItemList) {
            CircleImageView circleImageView = new   CircleImageView(this.activity);
            circleImageView.setName(navitem.getName());
            circleImageView.setBackground(this.activity.getResources().getDrawable(R.drawable.circle));
            circleImageView.setImageResource(R.drawable.ic_cloud);
            circleImageView.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            this.circleLayout.addView(circleImageView);
            Log.d("category", circleImageView.getName());
            this.circularImageLoader.displayImage(navitem.getImageIcon(), circleImageView);
        }
    }
}
