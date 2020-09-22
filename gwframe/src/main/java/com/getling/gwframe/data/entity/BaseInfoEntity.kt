package com.getling.gwframe.data.entity

import androidx.databinding.BaseObservable

/**
 * @Author: getling
 * @CreateDate: 2019/11/14 9:19
 * @Description:
 */
open class BaseInfoEntity : BaseEntity, BaseObservable() {
    var ip: String? = null
    var id: String? = null
}