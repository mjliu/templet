package com.mjliu.commonlib.retrofit;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.mjliu.commonlib.utils.MyUtil;

public class RetrofitHeader {
    /**
     * 手机系统版本
     */
    public static final String osVersion = android.os.Build.VERSION.RELEASE;
    /**
     * 手机型号
     */
    public static final String model = android.os.Build.MODEL;
    /**
     * 压缩方式
     */
    public static final String HTTP_PRESSED_TYPE = "gzip";

    /**
     * 渠道号
     */
    public static String APP_COMPANY = "";// 渠道名称
    /**
     * 手机串号
     */
    public static String imei = "";
    /**
     * 手机号码
     */
    public static String phoneNumber = "";
    /**
     * 包名
     */
    public static String packagename = "";
    /**
     * 版本2.x.x
     */
    public static String version = "";
    /**
     * 版本号23
     */
    public static int versionCode = 0;
    /**
     * 联网方式
     */
    public static String conn_mode = "";
    /**
     * 有无sim卡
     */
    public static String iscard = "";
    /**
     * 是否定位成功
     */
    public static int ispos = 0;

    public static okhttp3.Request.Builder getRequestBuilder(okhttp3.Request.Builder b) {
        if (!TextUtils.isEmpty(version)) {
            b.addHeader("VERSION", version);// 版本号
            b.addHeader("BVERSION", String.valueOf(versionCode));
        }

        b.addHeader("NETWORKTYPE", TextUtils.isEmpty(conn_mode) ? "" : conn_mode);// 联网方式
        b.addHeader("SERIALNUMBER", imei == null ? "" : imei);// 手机串号
        b.addHeader("MODEL", TextUtils.isEmpty(model) ? "" : model);// 手机型号
        b.addHeader("OSVERSION", TextUtils.isEmpty(osVersion) ? "" : osVersion);// 手机系统版本
        b.addHeader("PHONENUMBER", phoneNumber == null ? "" : phoneNumber);// 手机号码
        b.addHeader("posmode", "gps,wifi");// 定位方式
        b.addHeader("ISLOCATE", "" + ispos);// 是否定位成功
        b.addHeader("iscard", TextUtils.isEmpty(iscard) ? "" : iscard);// 有无sim卡
        b.addHeader("company", TextUtils.isEmpty(APP_COMPANY) ? "" : APP_COMPANY);// 渠道号
        b.addHeader("TYPE", "1");// 1:android 2:ios
        b.addHeader("PACKAGE", TextUtils.isEmpty(packagename) ? "" : packagename);// 包名
        return b;
    }

    public static void init(Context context) {
        try {
            packagename = context.getPackageName();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm.getSimState() == TelephonyManager.SIM_STATE_READY) {
                iscard = "1";
            } else {
                iscard = "0";
            }
            imei = MyUtil.getDeviceId(context).toString();
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
