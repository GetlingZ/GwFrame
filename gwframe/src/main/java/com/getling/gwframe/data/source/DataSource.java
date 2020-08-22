package com.getling.gwframe.data.source;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @Author: getling
 * @CreateDate: 2019/11/13 11:25
 * @Description: 获取数据的方法
 */
public interface DataSource {
    /**
     * 远程服务数据
     */
    interface Remote {
        void setActivity(AppCompatActivity activity);
    }

    /**
     * 本地数据
     */
    interface Local {
    }

    /**
     * 返回数据结构如下，1成功，其他数值失败，需要检查是否有错误信息
     * {
     * "flag": 1,
     * "errorMessage": null,
     * "logMessage": null,
     * "logUser": null,
     * "dataList":[]
     * }
     */
    interface DataCallBack<T> {
        void checkMessage(T t);

        void onSuccess(T t);

        boolean onFail(String msg);
    }
}