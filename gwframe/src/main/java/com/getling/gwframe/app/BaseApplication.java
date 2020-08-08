package com.getling.gwframe.app;

import android.app.Application;

/**
 * @Author: getling
 * @CreateDate: 2019/12/9 16:36
 * @Description:
 */
public class BaseApplication extends Application {
    public static Application appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}
