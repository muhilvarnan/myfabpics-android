package com.myfabpics.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myfabpics.DataClass.Category;
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

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";;
    private static final String KEY_CATEGORY_title = "title";
    private static final String KEY_CATEGORY_IMAGE = "image";
    private static final String KEY_CATEGORY_NAV_ICON = "nav_icon";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SUBSCRIBE_TABLE = "CREATE TABLE " + TABLE_SUBSCRIBE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT" + ")";
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY_title + " TEXT, "+ KEY_CATEGORY_IMAGE +" TEXT, "+KEY_CATEGORY_NAV_ICON+" TEXT)";
        db.execSQL(CREATE_SUBSCRIBE_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBSCRIBE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
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
        values.put(KEY_CATEGORY_title, category.getTitle());
        values.put(KEY_CATEGORY_IMAGE, category.getNavIcon());
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
}
