package com.myfabpics.Task;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

import com.myfabpics.CategoryList;
import com.myfabpics.CommonHelper.MakeHTTPCall;
import com.myfabpics.DataClass.Category;
import com.myfabpics.DataClass.Photo;
import com.myfabpics.DatabaseHandler.DatabaseHelper;
import com.myfabpics.WallActivity;
import com.myfabpics.config.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 25/7/16.
 */
public class CategoryListFetch extends AsyncTask<String, Integer, String> {

    private ProgressDialog progDialog;

    private CategoryList activity;

    String URL = "api/categories/";

    public CategoryListFetch(CategoryList activity) {
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
                API api = new API();
                this.URL = api.getBaseEndPoint()+ this.URL + String.valueOf(this.activity.getCategoryId());
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
        ArrayList<Photo> remoteCategoryImageList = new ArrayList<Photo>();
        DatabaseHelper dbDatabaseHelper = new DatabaseHelper(this.activity);
        if (result.length() != 0) {
            try {
                JSONObject categoryImageListJsonData = new JSONObject(result);
                JSONArray categoryImageList = categoryImageListJsonData.getJSONArray("items");
                int currentPage = Integer.parseInt(categoryImageListJsonData.optString("current_page").toString());
                int totalPage = Integer.parseInt(categoryImageListJsonData.optString("total_page").toString());
                for(int i=0; i < categoryImageList.length(); i++){
                    JSONObject jsonObject = categoryImageList.getJSONObject(i);
                    int id = Integer.parseInt(jsonObject.optString("id").toString());
                    String title = jsonObject.optString("title").toString();
                    String image = jsonObject.optString("image").toString();
                    int categoryId = 2;
                    remoteCategoryImageList.add(new Photo(id, categoryId, title, image));
                }
                dbDatabaseHelper.addOrUpdatePhoto(remoteCategoryImageList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.activity.setQuoteList(remoteCategoryImageList);
        progDialog.dismiss();
    }


}
