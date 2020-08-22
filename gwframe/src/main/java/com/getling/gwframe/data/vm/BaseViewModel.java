package com.getling.gwframe.data.vm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import com.getling.gwframe.data.source.DataSourceRepository;

/**
 * @Author: getling
 * @CreateDate: 2020/8/22 9:56
 * @Description:
 */
public class BaseViewModel extends ViewModel {

    public DataSourceRepository dataSource;

    public BaseViewModel(DataSourceRepository dataSource) {
        this.dataSource = dataSource;
    }

    public void setRemoteContext(AppCompatActivity activity) {
        dataSource.getRemoteDataSource().setActivity(activity);
    }
}
