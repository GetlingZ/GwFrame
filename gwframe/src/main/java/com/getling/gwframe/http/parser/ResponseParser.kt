package com.getling.gwframe.http.parser

import com.getling.gwframe.data.entity.BaseResponseEntity
import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.AbstractParser
import java.io.IOException
import java.lang.reflect.Type

/**
 * @Author: getling
 * @CreateDate: 2020/8/3 8:51
 * @Description:
 */
@Parser(name = "Response")
open class ResponseParser<T> : AbstractParser<T> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: Response): T {
        //获取泛型类型
        val type: Type = ParameterizedTypeImpl[Response::class.java, mType]
        //获取Response对象
        val data: BaseResponseEntity<T> = convert(response, type)
        //获取data字段
        val t = data.data
        if (data.code != 200 || t == null) {
            //code不等于200，说明数据不正确，抛出异常
            throw ParseException(data.code.toString(), data.msg, response)
        }
        return t
    }

}