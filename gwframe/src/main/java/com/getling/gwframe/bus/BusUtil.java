package com.getling.gwframe.bus;

import com.getling.gwframe.bus.event.BusEvent;
import com.getling.gwframe.bus.rxbus.RxBus;

/**
 * @Author: getling
 * @CreateDate: 2020/7/18 14:38
 * @Description:
 */
public class BusUtil {

    public static void postEvent(BusEvent event) {
        RxBus.getDefault().post(event);
    }

    /**
     * 必须先注册，再调用post方法，才能接收到回调
     */
    public static <T> void subscribe(Object subscriber, RxBus.BusCallback<T> callback) {
        RxBus.getDefault().subscribe(subscriber, callback);
    }

    public static void unregister(Object subscriber) {
        RxBus.getDefault().unregister(subscriber);
    }
}
