package com.getling.gwframe.sample;

import com.getling.gwframe.app.BaseApplication;
import com.getling.gwframe.app.GwFrame;
import com.getling.gwframe.data.source.DataSourceRepository;
import com.getling.gwframe.sample.data.LocalData;
import com.getling.gwframe.sample.data.RemoteData;
import com.getling.gwframe.sample.vm.MyVmFactory;

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
                .setBaseUrl("http://www.baidu.com")
                .setViewModelFactory(MyVmFactory.getInstance(DataSourceRepository.getInstance(new RemoteData(), new LocalData())));
    }
}
