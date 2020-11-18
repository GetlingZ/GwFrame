package com.getling.gwframe.http.parser

import android.text.TextUtils
import com.getling.gwframe.R
import com.getling.gwframe.utils.NotifyUtil
import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.AbstractParser
import java.io.IOException
import java.lang.reflect.Type

/**
 * @Author: getling
 * @CreateDate: 2020/11/18 10:23
 * @Description:
 */
@Parser(name = "DataList")
open class DataListParser<T> : AbstractParser<T> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: Response): T {
        val type: Type = ParameterizedTypeImpl[Response::class.java, mType] //获取泛型类型
        val data: DataListResponse<T> = convert(response, type)   //获取Response对象
        val t = data.dataList                          //获取data字段
        if (data.flag != 1 || t == null) { //code不等于1，说明数据不正确，抛出异常
            if (TextUtils.isEmpty(data.errorMessage)) {
                NotifyUtil.showError(R.string.msg_no_result)
            } else {
                NotifyUtil.showError(data.errorMessage)
            }
            throw ParseException(data.flag.toString(), data.errorMessage, response)
        }
        return t
    }
}