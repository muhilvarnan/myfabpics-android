package com.myfabpics.Task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.myfabpics.CommonHelper.MakeHTTPCall;
import com.myfabpics.CommonHelper.RemoteCategory;
import com.myfabpics.DataClass.Category;

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

    private Activity activity;

    String URL = "http://192.168.0.103:8000/api/categories/";

    public CategoryFetch(Activity activity) {
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
            String categoryData = httpCli.getData(this.URL);
            return categoryData;
        } catch (Exception e) {
            return new String();
        }
    }

    @Override
    protected void onPostExecute(String result)
    {

        List<Category> remoteCategoryList = new ArrayList<Category>();
        Log.d("fetch", result);
        progDialog.dismiss();
        if (result.length() == 0) {
            //this.activity.alert ("Unable to find image data. Try again later. ");
            return;
        }

        try {

            JSONArray categoryJsonData = new JSONArray(result);
            for(int i=0; i < categoryJsonData.length(); i++){
                JSONObject jsonObject = categoryJsonData.getJSONObject(i);
                int id = Integer.parseInt(jsonObject.optString("id").toString());
                String title = jsonObject.optString("title").toString();
                String navIcon = jsonObject.optString("nav_icon").toString();
                remoteCategoryList.add(new Category(id, title, navIcon));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //this.activity.setImages(imageData);

    }
}
