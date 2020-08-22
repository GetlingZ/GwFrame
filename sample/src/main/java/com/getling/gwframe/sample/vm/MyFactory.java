package com.getling.gwframe.sample.vm;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.getling.gwframe.data.source.DataSourceRepository;
import com.getling.gwframe.data.vm.ViewModelFactory;

/**
 * @Author: getling
 * @CreateDate: 2020/8/22 9:12
 * @Description:
 */
public class MyFactory extends ViewModelFactory {

    private static volatile MyFactory INSTANCE;
    private DataSourceRepository dataSourceRepository;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MyViewModel.class)) {
            return (T) new MyViewModel(dataSourceRepository);
        }
        return (T) new MyViewModel(dataSourceRepository);
    }

    private MyFactory(DataSourceRepository dataSourceRepository) {
        this.dataSourceRepository = dataSourceRepository;
    }

    public static synchronized MyFactory getInstance(DataSourceRepository dataSourceRepository) {
        if (INSTANCE == null) {
            INSTANCE = new MyFactory(dataSourceRepository);
        }
        return INSTANCE;
    }

    public static void destroy() {
        INSTANCE = null;
    }
}
