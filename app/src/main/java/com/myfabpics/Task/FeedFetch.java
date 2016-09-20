package com.myfabpics.Task;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.myfabpics.CommonHelper.MakeHTTPCall;
import com.myfabpics.DataClass.Category;
import com.myfabpics.DatabaseHandler.DatabaseHelper;
import com.myfabpics.WallActivity;
import com.myfabpics.config.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 4/9/16.
 */
public class FeedFetch extends AsyncTask<String, Integer, String> {

    private ProgressDialog progDialog;

    private WallActivity activity;

    String URL = "api/categories/";

    public FeedFetch(WallActivity activity) {
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
            API api = new API();
            this.URL = api.getBaseEndPoint()+this.URL;
            if (httpCli.checkInternetConnection(this.activity)) {
                String feedData = httpCli.getData(this.URL);
                return feedData;
            }
            return "";
        } catch (Exception e) {
            return new String();
        }
    }

    @Override
    protected void onPostExecute(String result)
    {
        List<Category> remoteFeedList = new ArrayList<Category>();
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
                    remoteFeedList.add(new Category(id, title, image, navIcon));
                }
                dbDatabaseHelper.reframeCategories(remoteFeedList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //this.activity.setNavigationData();
    }
}
