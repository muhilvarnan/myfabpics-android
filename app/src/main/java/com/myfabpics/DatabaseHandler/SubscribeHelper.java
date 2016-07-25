package com.myfabpics.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myfabpics.DataClass.Subcribe;

/**
 * Created by root on 23/7/16.
 */
public class SubscribeHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "myfabpics";

    // Contacts table name
    private static final String TABLE_SUBSCRIBE = "subscribe";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";

    public SubscribeHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SUBSCRIBE_TABLE = "CREATE TABLE " + TABLE_SUBSCRIBE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_SUBSCRIBE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBSCRIBE);
        onCreate(db);
    }

    public void addSubscribe(Subcribe subcribe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, subcribe.getEmail());
        db.insert(TABLE_SUBSCRIBE, null, values);
        db.close();
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
