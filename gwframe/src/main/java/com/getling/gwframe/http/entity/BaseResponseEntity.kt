package com.getling.gwframe.http.entity

/**
 * @Author: getling
 * @CreateDate: 2019/11/14 9:15
 * @Description:
 */
open class BaseResponseEntity<D> : BaseEntity {
    /**
     * 0成功
     */
    var code = 0
    var msg: String? = null
    var data: D? = null
}