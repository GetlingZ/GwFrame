package com.getling.gwframe.http.network;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.getling.gwframe.bus.BusUtil;
import com.getling.gwframe.bus.event.NetworkEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import rxhttp.RxHttp;

public class NetworkCheckService extends Service {

    public static String URL_CHECK = "";
    /**
     * 每过半个小时请求一次
     */
    public static final int DELAY_SEC = 5 * 60;

    public NetworkCheckBinder binder;
    public Disposable disposable;

    public NetworkCheckService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        checkNetwork();
    }

    @Override
    public IBinder onBind(Intent intent) {
        binder = new NetworkCheckBinder();
        return binder;
    }

    public void checkNetwork() {
        RxHttp.get(URL_CHECK)
                .asString()
                .timeout(30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen(objectObservable -> objectObservable.flatMap((Function<Object, ObservableSource<?>>) o -> {
                    return Observable.just(0).delay(DELAY_SEC, TimeUnit.SECONDS);
                }))
                .retryWhen(throwableObservable -> throwableObservable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                    BusUtil.postEvent(new NetworkEvent());
                    return Observable.just(0).delay(DELAY_SEC, TimeUnit.SECONDS);
                })).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                BusUtil.postEvent(new NetworkEvent());
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public static class NetworkCheckBinder extends Binder {
        public boolean isAvailable() {
            return true;
        }
    }
}
