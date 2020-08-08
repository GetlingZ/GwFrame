package com.getling.gwframe.http.entity

import androidx.databinding.BaseObservable
import com.getling.gwframe.http.entity.BaseEntity

/**
 * @Author: getling
 * @CreateDate: 2019/11/14 9:19
 * @Description:
 */
open class BaseInfoEntity : BaseEntity, BaseObservable() {
    var ip = ""
}