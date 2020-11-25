package com.getling.gwframe.http.parser

import java.io.Serializable

/**
 * @Author: getling
 * @CreateDate: 2020/11/18 10:28
 * @Description:
 */
class EntityResponse<T> : Serializable {
    var flag = 0
    var errorMessage: String? = null
    var logMessage: String? = null
    var entity: T? = null
}