package com.mjliu.commonlib.retrofit;

/**
 * Created by Administrator on 2017/11/29.
 */
public abstract class Callback<T> {
    public abstract void succ(T t);

    public abstract void fail(int code, String msg);
}
