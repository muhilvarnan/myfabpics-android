package com.myfabpics.DataClass;

import android.text.Editable;

/**
 * Created by root on 23/7/16.
 */
public class Subcribe {

    int _id;
    String _email;

    public Subcribe(String email) {
        this._email = email;
    }

    public Subcribe(int id, String email) {

        this._id=id;
        this._email=email;
    }

    public int getId() {
        return this._id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getEmail() {
        return this._email;
    }

    public void getEmail(String email) {
        this._email=email;
    }
}
