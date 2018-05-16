package com.mjliu.commonlib.utils;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

public class UtilsLog {

    public static boolean isTest = true;
    public static boolean saveLogfile = false;

    public static void d(String key, String value) {
        if (isTest) {
            if (!TextUtils.isEmpty(value)) {
                Logger.d("log_test" + key, value);
            }
        }
    }

    public static void i(String key, String value) {
        if (isTest) {
            if (!TextUtils.isEmpty(value)) {
                Logger.i("log_test" + key, value);
            }
        }
    }

    public static void e(String key, String value) {
        if (isTest) {
            if (!TextUtils.isEmpty(value)) {
                Logger.e("log_test" + key, value);
            }
        }
    }
}
