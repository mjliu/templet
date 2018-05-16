package com.mjliu.commonlib.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private RetrofitHelper() {}

    public static Retrofit getRetrofit(String url) {
        return new Retrofit.Builder()
                .client(ClientHelper.getInstance().getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
