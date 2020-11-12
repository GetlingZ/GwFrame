package com.getling.gwframe.http;

import com.blankj.utilcode.util.SPUtils;
import com.getling.gwframe.app.GwFrame;
import com.getling.gwframe.constant.SPConstant;

import rxhttp.wrapper.annotation.DefaultDomain;

/**
 * @Author: getling
 * @CreateDate: 2019/11/20 8:57
 * @Description: 服务器地址
 */
public class BaseUrl {

    /**
     * 登录成功后，选择的工厂服务器地址
     */
    @DefaultDomain
    public static String BASE_URL = SPUtils.getInstance().getString(
            SPConstant.SP_BASE_URL,
            GwFrame.getInstance().factory.getBaseUrl());

    public static void saveBaseUrl(String url) {
        BASE_URL = url;
        SPUtils.getInstance().put(SPConstant.SP_BASE_URL, BASE_URL);
    }
}
