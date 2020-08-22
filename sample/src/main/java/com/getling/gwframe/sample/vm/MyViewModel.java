package com.getling.gwframe.sample.vm;

import com.getling.gwframe.data.source.DataSourceRepository;
import com.getling.gwframe.data.vm.BaseViewModel;

import org.jetbrains.annotations.NotNull;

/**
 * @Author: getling
 * @CreateDate: 2020/8/21 10:33
 * @Description:
 */
public class MyViewModel extends BaseViewModel {

    public MyViewModel(@NotNull DataSourceRepository dataSourceRepository) {
        super(dataSourceRepository);
    }
}
