package com.getling.gwframe.http.observer;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @Author: getling
 * @CreateDate: 2019/11/20 15:52
 * @Description:
 */
public abstract class SimpleObserver<T> implements Observer<T> {

    public abstract void onSuccess(T t);

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
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

    }
}
