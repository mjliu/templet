package com.mjliu.commonlib.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.mjliu.commonlib.R;

public class MyCustomProgressDlg extends Dialog {

    public MyCustomProgressDlg(Context context) {
        this(context, R.style.mycustomprogressdlg);
        setContentView(R.layout.mycustomprogressdlg);
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    private MyCustomProgressDlg(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }
}
