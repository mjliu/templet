package com.mjliu.templet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mjliu.commonlib.retrofit.BaseObserver;
import com.mjliu.commonlib.utils.UtilsLog;
import com.mjliu.templet.entity.CurrentOrderEntity;
import com.mjliu.templet.retrofit.RetrofitManager;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetrofitManager.getInstance().currentOrder(new BaseObserver<CurrentOrderEntity>() {
            @Override
            public void succ(CurrentOrderEntity o) {
                UtilsLog.e(TAG, "success ---------- ");
            }

            @Override
            public void fail(int code, String msg) {
                UtilsLog.e(TAG, msg);
            }
        });
    }
}
