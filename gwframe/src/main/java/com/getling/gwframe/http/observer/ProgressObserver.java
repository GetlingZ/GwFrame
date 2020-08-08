package com.getling.gwframe.http.observer;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.getling.gwframe.utils.DialogUtil;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * @Author: getling
 * @CreateDate: 2019/11/20 9:17
 * @Description:
 */
public abstract class ProgressObserver<T> implements Observer<T> {
    private AlertDialog dialog;
    private Disposable disposable;
    private Context context;

    public ProgressObserver(Context context) {
        this(context, false);
    }

    public ProgressObserver(Context context, boolean cancelable) {
        this.context = context;
        dialog = DialogUtil.createProgressDialog(context, cancelable);
        dialog.setOnDismissListener(dialog -> {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        });
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        if (dialog != null && !dialog.isShowing()) {
            if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
                dialog.show();
            }
        }
    }

    @Override
    public void onNext(T t) {
        dismiss();
        onSuccess(t);
    }

    public abstract void onSuccess(@NotNull T t);

    @Override
    public void onError(Throwable e) {
        dismiss();
        String msg;
        if (e == null) {
            msg = "网络错误，请稍后再试";
        } else {
            e.printStackTrace();
            msg = e.getMessage();
        }
        if (StringUtils.isEmpty(msg) || msg.contains("Failed") || msg.contains("failed")) {
            msg = "网络错误，请稍后再试";
        }
        ToastUtils.showLong(msg);
    }

    @Override
    public void onComplete() {
        dismiss();
    }

    private void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
