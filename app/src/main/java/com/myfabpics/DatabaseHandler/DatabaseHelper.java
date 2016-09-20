package com.myfabpics.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myfabpics.DataClass.Category;
import com.myfabpics.DataClass.Photo;
import com.myfabpics.DataClass.Subcribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 23/7/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "myfabpics";

    // Contacts table name
    private static final String TABLE_SUBSCRIBE = "subscribe";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_PHOTOS = "photos";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";;
    private static final String KEY_TITLE = "title";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_CATEGORY_NAV_ICON = "nav_icon";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SUBSCRIBE_TABLE = "CREATE TABLE " + TABLE_SUBSCRIBE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT" + ")";
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT, "+ KEY_IMAGE +" TEXT, "+KEY_CATEGORY_NAV_ICON+" TEXT)";
        String CREATE_PHOTO_TABLE = "CREATE TABLE " + TABLE_PHOTOS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY_ID + " INTEGER, " + KEY_TITLE + " TEXT, "+ KEY_IMAGE +" TEXT)";
        db.execSQL(CREATE_SUBSCRIBE_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_PHOTO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBSCRIBE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        onCreate(db);
    }

    public void addSubscribe(Subcribe subcribe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, subcribe.getEmail());
        db.insert(TABLE_SUBSCRIBE, null, values);
        db.close();
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, category.getId());
        values.put(KEY_TITLE, category.getTitle());
        values.put(KEY_IMAGE, category.getNavIcon());
        values.put(KEY_CATEGORY_NAV_ICON, category.getNavIcon());
        db.insert(TABLE_CATEGORIES, null, values);
        db.close();
    }

    public void reframeCategories(List<Category> categoryList) {
        emptyCategory();
        for(Category category: categoryList) {
            addCategory(category);
        }
    }

    public void addPhoto(Photo photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, photo.getId());
        values.put(KEY_TITLE, photo.getTitle());
        values.put(KEY_IMAGE, photo.getImageUrl());
        db.insert(TABLE_CATEGORIES, null, values);
        db.close();
    }

    public int updatePhoto(Photo photo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, photo.getTitle());
        values.put(KEY_IMAGE, photo.getImageUrl());

        // updating row
        return db.update(TABLE_PHOTOS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(photo.getId()) });
    }

    public  Photo getPhoto(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PHOTOS, new String[] { KEY_ID, KEY_CATEGORY_ID,
                        KEY_TITLE, KEY_IMAGE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            Photo photo = new Photo(Integer.parseInt(cursor.getString(0)),
                                    Integer.parseInt(cursor.getString(1)),
                                    cursor.getString(2),
                                    cursor.getString(3));
            // return contact
            return photo;
        } else {
            Photo photo  = new Photo();
            photo.setId(0);
            return photo;
        }
    }

    public void addOrUpdatePhoto(List<Photo> photoList) {
        for(Photo photoitem: photoList){
            Photo photo = getPhoto(photoitem.getId());
            if(photo.getId()==0) {
                addPhoto(photo);
            } else {
                updatePhoto(photo);
            }
        }
    }

    public void emptyCategory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_CATEGORIES);
        db.close();
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category quote = new Category();
                quote.setId(Integer.parseInt(cursor.getString(0)));
                quote.setTitle(cursor.getString(1));
                quote.setImage(cursor.getString(2));
                quote.setNavIcon(cursor.getString(3));
                categories.add(quote);
            } while (cursor.moveToNext());
        }
        return categories;
    }

    public ArrayList<Photo> getPhotoList() {
        ArrayList<Photo> photos = new ArrayList<Photo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PHOTOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Photo photo = new Photo();
                photo.setId(Integer.parseInt(cursor.getString(0)));
                photo.setCategoryId(Integer.parseInt(cursor.getString(1)));
                photo.setTitle(cursor.getString(1));
                photo.setImageUrl(cursor.getString(2));
                photos.add(photo);
            } while (cursor.moveToNext());
        }
        return photos;
    }

    public int getSubscribeCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_SUBSCRIBE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor != null && !cursor.isClosed()){
            count = cursor.getCount();
            cursor.close();
        }
        db.close();
        return count;
    }

    public Category getCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORIES, new String[] { KEY_ID,
                        KEY_TITLE, KEY_IMAGE, KEY_CATEGORY_NAV_ICON }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            Category category = new Category(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3));
            // return contact
            return category;
        } else {
            Category category = new Category();
            category.setId(0);
            return category;
        }
    }

}
