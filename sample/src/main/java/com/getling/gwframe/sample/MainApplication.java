package com.getling.gwframe.sample;

import com.getling.gwframe.app.BaseApplication;
import com.getling.gwframe.app.GwFrame;

/**
 * @Author: getling
 * @CreateDate: 2020/7/20 17:01
 * @Description:
 */
public class MainApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        GwFrame.getInstance()
                .setAdaptWidth(1280)
                .isDebug(true)
                .setBaseUrl("http://www.baidu.com");
    }
}
