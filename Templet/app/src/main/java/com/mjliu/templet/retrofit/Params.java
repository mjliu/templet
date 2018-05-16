package com.mjliu.templet.retrofit;


import android.text.TextUtils;
import java.util.HashMap;

/**
 * Created by Kellan on 2017/9/27.
 */
public class Params extends HashMap<String, String> {

    public Params() {
    }

    public void build() {
//        put("token", SharePerUtils.getToken());
    }

    @Override
    public String put(String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            return super.put(key, value);
        } else {
            return "";
        }
    }
}

