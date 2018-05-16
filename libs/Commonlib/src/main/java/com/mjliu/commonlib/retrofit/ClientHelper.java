package com.mjliu.commonlib.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class ClientHelper {
    public final static int CONNECT_TIMEOUT = 60;
    public final static int READ_TIMEOUT = 60;
    public final static int WRITE_TIMEOUT = 60;
    private OkHttpClient mClient;
    private ClientHelper() {
    }

    public static ClientHelper getInstance() {
        return Holder.sInstance;
    }

    private static class Holder {
        private static ClientHelper sInstance = new ClientHelper();
    }

    private void init() {
        mClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .addInterceptor(new CommonInterceptor())
                .build();
    }

    public OkHttpClient getClient() {
        if (mClient == null) {
            init();
        }
        return mClient;
    }
}
