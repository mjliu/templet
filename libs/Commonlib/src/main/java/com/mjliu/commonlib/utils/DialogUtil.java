package com.mjliu.commonlib.utils;

import android.app.Activity;
import android.view.Gravity;

import com.mjliu.commonlib.R;

public class DialogUtil {

    private DialogUtil() {
    }

    public static MyCustomProgressDlg getProgressDialog(Activity activity) {
        if (MyUtil.isDead(activity))
            return null;

        MyCustomProgressDlg progressDialog = new MyCustomProgressDlg(activity);
        progressDialog.setContentView(R.layout.mycustomprogressdlg);
        progressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        progressDialog.setCancelable(false);

        return progressDialog;
    }

    public static void cancelProgressDialog(Activity activity, MyCustomProgressDlg progressDialog) {
        if (MyUtil.isDead(activity))
            return;

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
