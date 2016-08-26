package com.myfabpics.Task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.myfabpics.CommonHelper.CircleNav;
import com.myfabpics.CommonHelper.MakeHTTPCall;
import com.myfabpics.CommonHelper.RemoteCategory;
import com.myfabpics.DataClass.Category;
import com.myfabpics.DataClass.NavItem;
import com.myfabpics.DatabaseHandler.DatabaseHelper;
import com.myfabpics.R;
import com.myfabpics.WallActivity;
import com.szugyi.circlemenu.view.CircleLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 25/7/16.
 */
public class CategoryFetch extends AsyncTask<String, Integer, String> {

    private ProgressDialog progDialog;

    private WallActivity activity;

    String URL = "http://myfabpics.com/api/categories/";

    public CategoryFetch(WallActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDialog = ProgressDialog.show(this.activity, "Please wait...", "processing", true, false);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            MakeHTTPCall httpCli = new MakeHTTPCall();
            if (httpCli.checkInternetConnection(this.activity)) {
                String categoryData = httpCli.getData(this.URL);
                return categoryData;
            }
            return "";
        } catch (Exception e) {
            return new String();
        }
    }

    @Override
    protected void onPostExecute(String result)
    {
        List<Category> remoteCategoryList = new ArrayList<Category>();
        DatabaseHelper dbDatabaseHelper = new DatabaseHelper(this.activity);
        progDialog.dismiss();
        if (result.length() != 0) {
            try {
                JSONArray categoryJsonData = new JSONArray(result);
                for(int i=0; i < categoryJsonData.length(); i++){
                    JSONObject jsonObject = categoryJsonData.getJSONObject(i);
                    int id = Integer.parseInt(jsonObject.optString("id").toString());
                    String title = jsonObject.optString("title").toString();
                    String navIcon = jsonObject.optString("nav_icon").toString();
                    String image = jsonObject.optString("image").toString();
                    remoteCategoryList.add(new Category(id, title, image, navIcon));
                }
                dbDatabaseHelper.reframeCategories(remoteCategoryList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.activity.setNavigationData();
    }
}
