package com.mjliu.commonlib.retrofit;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.PhoneUtils;

public class RetrofitHeader {
    @SuppressLint("MissingPermission")
    public static okhttp3.Request.Builder getRequestBuilder(okhttp3.Request.Builder b) {
        b.addHeader("VERSION", AppUtils.getAppVersionName());// 版本号
        b.addHeader("BVERSION", String.valueOf(AppUtils.getAppVersionCode()));
        b.addHeader("NETWORKTYPE", String.valueOf(NetworkUtils.getNetworkType()));// 联网方式
        b.addHeader("SERIALNUMBER", PhoneUtils.getIMEI());// 手机串号
        b.addHeader("MODEL", DeviceUtils.getModel());// 手机型号
        b.addHeader("OSVERSION", DeviceUtils.getSDKVersionName());// 手机系统版本
        b.addHeader("PHONENUMBER", "");// 手机号码
        b.addHeader("posmode", "gps,wifi");// 定位方式
        b.addHeader("iscard", PhoneUtils.isSimCardReady() ? "yes" : "no");// 有无sim卡
        b.addHeader("TYPE", "1");// 1:android 2:ios
        b.addHeader("PACKAGE", AppUtils.getAppPackageName());// 包名
        return b;
    }
}
