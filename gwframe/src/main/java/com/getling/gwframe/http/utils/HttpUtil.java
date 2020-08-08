package com.getling.gwframe.http.utils;

import rxhttp.RxHttp;

/**
 * @Author: getling
 * @CreateDate: 2020/7/20 9:22
 * @Description:
 */
public class HttpUtil {
    public static void init() {
        RxHttp.get("");
        RxHttp.postForm("");
        RxHttp.postJson("");
        RxHttp.postJsonArray("");
    }
}
