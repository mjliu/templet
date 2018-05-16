package com.mjliu.templet;

import android.support.test.rule.ActivityTestRule;

import com.blankj.utilcode.util.ToastUtils;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created by dubin on 16/9/28.
 */

public class HomeMainScreenActTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testStart() {
        MainActivity act = mActivityRule.getActivity();
        act.runOnUiThread(() -> {
            ToastUtils.showLong("afasdfsdf");
        });


        try {
            Thread.sleep(1000 * 60 * 100);
        } catch (Exception e) {

        }
    }
}
