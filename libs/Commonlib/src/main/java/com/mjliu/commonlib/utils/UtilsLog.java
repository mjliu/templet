package com.mjliu.commonlib.utils;

import android.text.TextUtils;
import android.util.Log;

public class UtilsLog {

    public static boolean isTest = true;

    public static void d(String key, String value) {
        if (isTest) {
            if (!TextUtils.isEmpty(value)) {
                Log.d("log_test" + key, value);
            }

        }
    }

    public static void i(String key, String value) {
        if (isTest) {
            if (!TextUtils.isEmpty(value)) {
                Log.i("log_test" + key, value);
            }
        }
    }

    public static void e(String key, String value) {
        if (isTest) {
            if (!TextUtils.isEmpty(value)) {
                Log.e("log_test" + key, value);
            }
        }
    }
}
