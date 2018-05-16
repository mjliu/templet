package com.mjliu.commonlib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

import static android.content.Context.TELEPHONY_SERVICE;

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

    public static double strToDouble(String s) {
        if (TextUtils.isEmpty(s))
            return -1;
        try {
            return Double.valueOf(s);
        } catch (Exception e) {
        }

        return -2;
    }

    public static boolean isSame(String s1, String s2) {
        if (TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2))
            return false;

        return s1.equals(s2);

    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long s, String format) {
        String res = "";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = new Date(s);
            res = simpleDateFormat.format(date);
        } catch (Exception e) {

        }
        return res;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showSoftKeyboard(View view, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    public static Observable<Integer> countDown(long millisec, final int time) {
        return Observable.interval(0, millisec, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread(), false, 100)
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return time - aLong.intValue();
                    }
                })
                .take(time);
    }

    public static int getResIdByName(Context ctx, String name) {
        int id = -1;
        try {
            id = ctx.getResources().getIdentifier(name, "mipmap", ctx.getPackageName());
        } catch (Exception e) {
        }
        return id;
    }

    /**
     * 通过设置Camera打开闪光灯
     *
     * @param camera
     */
    public static void turnLightOnCamera(Activity activity, Camera camera) {
        final PackageManager pm = activity.getPackageManager();
        final FeatureInfo[] features = pm.getSystemAvailableFeatures();
        for (final FeatureInfo f : features) {
            if (PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) { // 判断设备是否支持闪光灯
                if (null == camera) {
                    camera = Camera.open();
                }
                try {
                    camera.setPreviewTexture(new SurfaceTexture(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
            }
        }
    }

    /**
     * 通过设置Camera关闭闪光灯
     *
     * @param camera
     */
    public static void turnLightOffCamera(Activity activity, Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        List<String> flashModes = parameters.getSupportedFlashModes();
        String flashMode = parameters.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            // 关闭闪光灯
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.stopPreview();
            }
        }
    }

    public static UUID getDeviceId(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences("device_id.xml", 0);
        final String id = prefs.getString("device_id", null);

        UUID uuid;
        if (id != null) {
            // Use the ids previously computed and stored in the prefs file
            uuid = UUID.fromString(id);
        } else {

            final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

            // Use the Android ID unless it's broken, in which case fallback on deviceId,
            // unless it's not available, then fallback on a random number which we store
            // to a prefs file
            try {
                if (!"9774d56d682e549c".equals(androidId)) {
                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                } else {
                    final String deviceId = ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getDeviceId();
                    uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            // Write the value out to the prefs file
            prefs.edit().putString("device_id", uuid.toString()).commit();
        }

        return uuid;
    }

    /**
     * 获取meta data 值
     *
     * @param context
     * @param key
     * @return
     */
    private static String getMetaData(Context context, String key) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @return true 表示开启
     */
    public static boolean isGpsOPen(Context ctx) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(ctx.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    /**
     * 追加写入文件
     *
     * @param content
     */
    public static void writeToFile(String fileName, String content) {
        String folderPath = Environment.getExternalStorageDirectory()
                + File.separator;
        File fileDir = new File(folderPath);
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                return;
            }
        }
        File file;
        //判断文件名是否为空
        if (TextUtils.isEmpty(fileName)) {
            file = new File(folderPath + "app_log.txt");
        } else {
            file = new File(folderPath + fileName);
        }

        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content + "\r\n");
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean pingHost(String str) {  //str  为要ping的IP地址
        boolean result = false;
        try {
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " + str);
            int status = p.waitFor();
            if (status == 0) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {

        }
        return result;
    }
}
