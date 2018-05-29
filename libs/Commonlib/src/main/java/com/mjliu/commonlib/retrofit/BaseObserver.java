package com.mjliu.commonlib.retrofit;

import android.app.Activity;
import android.app.Dialog;

import com.mjliu.commonlib.entity.BaseEntity;
import com.mjliu.commonlib.utils.DialogUtil;
import com.mjliu.commonlib.utils.MyUtil;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T extends BaseEntity> extends Callback<T> implements Observer<T> {
    private WeakReference<Dialog> weakDialog;
    private WeakReference<Activity> weakAct;

    public BaseObserver() {
    }

    public BaseObserver(Activity activity) {
        weakAct = new WeakReference<>(activity);
    }

    public void showDialog() {
        if (weakAct == null || MyUtil.isDead(weakAct.get()))
            return;
        weakDialog = new WeakReference<>(DialogUtil.getProgressDialog(weakAct.get()));
        this.weakDialog.get().show();
    }

    private void dissmiss() {
        if (weakDialog == null || weakDialog.get() == null)
            return;

        if (weakDialog.get().isShowing()) {
            weakDialog.get().dismiss();
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }

    @Override
    public void onNext(@NonNull T t) {
        if (weakAct != null && MyUtil.isDead(weakAct.get()))
            return;

        if (t == null) {
            fail(ErrorCode.CODE_PARSE_ERROR, ErrorCode.MSG_PARSE_ERROR);
            return;
        }

        String status = t.getStatus();
        String message = t.getMsg();

        if ("Y".equals(status)) {
            succ(t);
            return;
        }

        if ("N".equals(status) || status == null) {
            fail(ErrorCode.CODE_STATUS_N, message);
            return;
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (weakAct != null && MyUtil.isDead(weakAct.get()))
            return;
        dissmiss();

        ErrorStatus es = ErrorStatus.getStatus(e);
        fail(es.code, es.msg);
    }

    @Override
    public void onComplete() {
        if (weakAct != null && MyUtil.isDead(weakAct.get()))
            return;
        dissmiss();
    }
}
