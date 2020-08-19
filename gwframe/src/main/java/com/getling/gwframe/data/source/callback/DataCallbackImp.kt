package com.getling.gwframe.data.source.callback

import android.text.TextUtils
import com.blankj.utilcode.util.ToastUtils
import com.getling.gwframe.data.entity.BaseResponseEntity
import com.getling.gwframe.data.source.DataSource

/**
 * @Author: getling
 * @CreateDate: 2019/11/18 17:17
 * @Description:
 */
abstract class DataCallbackImp<T> : DataSource.DataCallBack<T> {

    override fun checkMessage(t: T) {
        if (t is BaseResponseEntity<*>) {
            if (onFail(t.msg)) {
                return
            }
        }
        onSuccess(t)
    }

    /**
     * 如果返回数据有错误信息，就判定请求失败，并打印信息
     */
    override fun onFail(msg: String?): Boolean {
        return if (TextUtils.isEmpty(msg)) {
            false
        } else {
            ToastUtils.showLong(msg)
            true
        }
    }
}