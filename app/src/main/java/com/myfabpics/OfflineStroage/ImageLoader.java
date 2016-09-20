package com.myfabpics.OfflineStroage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.myfabpics.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by root on 25/8/16.
 */
public class ImageLoader {
    MemoryCache memoryCache = new MemoryCache();
    FileCache fileCache;

    private Map<ImageView, String> imageviews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());

    ExecutorService executorService;

    public ImageLoader(Context context) {
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
    }

    final int loader_image = R.drawable.loader;

    public void displayImage(String url, ImageView imageView) {
        imageviews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if(bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            queuePhoto(url, imageView);
            imageView.setImageResource(loader_image);
        }
    }

    private void queuePhoto(String url, ImageView imageView) {
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    private Bitmap getBitmap(String url) {
        File f = fileCache.getFile(url);

        Bitmap b = decodeFile(f);
        if(b != null) {
            return b;
        }
        try {
            Bitmap bimap = null;
            URL imageurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);

            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.copyStream(is, os);
            os.close();
            bimap = decodeFile(f);
            return bimap;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    private  Bitmap decodeFile(File f) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            final int REQUIRED_SIZE = 1000;
            int width_tmp = o.outWidth, height_tmp   = o.outHeight;
            int scale = 1;
            Log.d("imageloader - width", String.valueOf(width_tmp));
            Log.d("imageloader - height", String.valueOf(height_tmp));
            while(true) {
                if(width_tmp/2 < REQUIRED_SIZE || height_tmp/2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Log.d("imageloader", String.valueOf(scale));
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class PhotoToLoad {
        public String url;
        public ImageView imageView;
        public PhotoToLoad(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad  photoToLoad;
        PhotosLoader(PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }
        @Override
        public void run() {
            if(imageViewReused(photoToLoad)) {
                return;
            }

            Bitmap bmp = getBitmap(photoToLoad.url);
            bmp = Bitmap.createScaledBitmap(bmp, 400, 267, true);
            memoryCache.put(photoToLoad.url, bmp);

            if(imageViewReused(photoToLoad)) {
                return;
            }
            if (bmp != null) {
                photoToLoad.imageView.setImageBitmap(bmp);
            } else {
                photoToLoad.imageView.setImageResource(loader_image);
            }
        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageviews.get(photoToLoad.imageView);
        if(tag == null || !tag.equals(photoToLoad.url)) {
            return true;
        } else {
            return false;
        }
    }

    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            photoToLoad = p;

        }

        @Override
        public void run() {
            if(imageViewReused(photoToLoad)) {
                return;
            }
            if (bitmap != null) {
                photoToLoad.imageView.setImageBitmap(bitmap);
            } else {
                photoToLoad.imageView.setImageResource(loader_image);
            }
        }
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }
}
