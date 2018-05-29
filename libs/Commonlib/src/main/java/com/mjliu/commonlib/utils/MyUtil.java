package com.mjliu.commonlib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;

/**
 * @author
 */
public class MyUtil {
    private static final String TAG = MyUtil.class.getSimpleName();

    @SuppressLint("NewApi")
    public static boolean isDead(Activity act) {
        return act == null || act.isFinishing() || act.isDestroyed();
    }

    public static int strToInt(String s) {
        if (TextUtils.isEmpty(s))
            return -1;

        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
        }

        return -2;
    }
}
