package com.myfabpics.CommonHelper;

import android.text.TextUtils;

/**
 * Created by root on 23/7/16.
 */
public class Validator {

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}

