package com.getling.gwframe.sample.vm;

import androidx.lifecycle.ViewModel;

import com.getling.gwframe.data.source.DataSource;
import com.getling.gwframe.data.source.DataSourceRepository;

/**
 * @Author: getling
 * @CreateDate: 2020/8/21 10:33
 * @Description:
 */
public class MyViewModel extends ViewModel {
    private DataSourceRepository dataSource;

    public MyViewModel get() {
        dataSource.getRemoteDataSource();
        return new MyViewModel();
    }
}
