package com.mjliu.templet.retrofit;

import com.mjliu.commonlib.entity.BaseEntity;
import com.mjliu.templet.entity.CurrentOrderEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("user/loc")
    Observable<BaseEntity> userLoc(@FieldMap Map<String, String> body);


    @FormUrlEncoded
    @POST("/order/currentOrder/")
    Observable<CurrentOrderEntity> currentOrder(@Field("token") String token, @Field("apptest") String apptest);

}
