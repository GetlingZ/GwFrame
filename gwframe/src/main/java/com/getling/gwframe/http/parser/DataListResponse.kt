package com.getling.gwframe.http.parser

import java.io.Serializable

/**
 * @Author: getling
 * @CreateDate: 2020/11/18 10:29
 * @Description:
 */
class DataListResponse<T> : Serializable {
    var flag = 0
    var errorMessage: String? = null
    var logMessage: String? = null
    var dataList: T? = null
}