package com.myfabpics.CommonHelper;

import android.util.Log;

import com.myfabpics.DataClass.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 24/7/16.
 */
public class RemoteCategory {

    String URL = "http://192.168.0.103:8000/api/categories/";

    public List<Category> getCategory() {
        MakeHTTPCall httpCli = new MakeHTTPCall();
        List<Category> remoteCategoryList = new ArrayList<Category>();
        try {
            String categoryData = httpCli.getData(this.URL);
            JSONArray categoryJsonData = new JSONArray(categoryData);
            for(int i=0; i < categoryJsonData.length(); i++){
                JSONObject jsonObject = categoryJsonData.getJSONObject(i);
                int id = Integer.parseInt(jsonObject.optString("id").toString());
                String title = jsonObject.optString("title").toString();
                String navIcon = jsonObject.optString("nav_icon").toString();
                remoteCategoryList.add(new Category(id, title, navIcon));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return remoteCategoryList;
    }
}
