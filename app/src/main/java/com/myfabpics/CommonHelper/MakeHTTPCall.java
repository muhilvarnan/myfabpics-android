package com.myfabpics.CommonHelper;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by root on 24/7/16.
 */
public class MakeHTTPCall {

    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public String getData(String url) throws IOException {
        Log.d("http", url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = this.client.newCall(request).execute();
        return response.body().string();
    }
}
