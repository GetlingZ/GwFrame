package com.getling.gwframe.data.source

/**
 * @Author: getling
 * @CreateDate: 2019/11/13 11:25
 * @Description: 获取数据的方法
 */
interface DataSource {
    /**
     * 远程服务数据
     */
    interface Remote {}

    /**
     * 本地数据
     */
    interface Local {}

    /**
     * 返回数据结构如下，1成功，其他数值失败，需要检查是否有错误信息
     * {
     *  "flag": 1,
     *  "errorMessage": null,
     *  "logMessage": null,
     *  "logUser": null,
     *  "dataList":[]
     * }
     */
    interface DataCallBack<T> {
        fun checkMessage(t: T)
        fun onSuccess(t: T)
        fun onFail(msg: String?): Boolean
    }
}