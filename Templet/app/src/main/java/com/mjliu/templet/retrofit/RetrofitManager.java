package com.mjliu.templet.retrofit;

import com.mjliu.commonlib.retrofit.RetrofitHelper;
import com.mjliu.templet.BuildConfig;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RetrofitManager {
    private  ApiService gpsService;

    private RetrofitManager() {
        initService();
    }

    private static class Holder {
        private static RetrofitManager sInstance = new RetrofitManager();
    }

    private static <T> T createService(String baseUrl, Class<T> service) {
        return RetrofitHelper.getRetrofit(baseUrl).create(service);
    }

    private void initService() {
        gpsService = createService(BuildConfig.API_HOST, ApiService.class);
    }

    private static <T> void toSubscribe(Observable<T> o, Observer<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public static RetrofitManager getInstance() {
        return Holder.sInstance;
    }

    public void userLoc(Params p, Observer s) {
        toSubscribe(gpsService.userLoc(p), s);
    }
}
