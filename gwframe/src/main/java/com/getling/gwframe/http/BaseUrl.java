package com.getling.gwframe.http;

import com.blankj.utilcode.util.SPUtils;
import com.getling.gwframe.constant.SPConstant;

import rxhttp.wrapper.annotation.DefaultDomain;

/**
 * @Author: getling
 * @CreateDate: 2019/11/20 8:57
 * @Description: 服务器地址
 */
public class BaseUrl {

    /**
     * 正式服务地址
     */
    private static String BASE_URL_PRO;
    /**
     * 测试服务地址
     */
    private static String BASE_URL_TEST;
    /**
     * 开发服务地址
     */
    private static String BASE_URL_DEV;

    /**
     * 登录成功后，选择的工厂服务器地址
     */
    @DefaultDomain
    public static String BASE_URL = SPUtils.getInstance().getString(SPConstant.SP_BASE_URL, BASE_URL_PRO);

    /**
     * 下载apk地址
     */
    private static String DOWNLOAD_APK_DEV;
    private static String DOWNLOAD_APK_TEST;
    private static String DOWNLOAD_APK_PRO;
    public static String DOWNLOAD_APK;

    public static void saveBaseUrl(String url) {
        BASE_URL = url;
        SPUtils.getInstance().put(SPConstant.SP_BASE_URL, BASE_URL);
    }
}
