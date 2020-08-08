package com.getling.gwframe.app;

import android.graphics.Color;
import android.view.Gravity;

import com.blankj.utilcode.util.ToastUtils;
import com.getling.gwframe.config.AppConfig;
import com.getling.gwframe.ftp.FTPConfig;
import com.getling.gwframe.http.BaseUrl;
import com.getling.gwframe.http.utils.HttpUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import rxhttp.RxHttp;

/**
 * @Author: getling
 * @CreateDate: 2020/7/18 17:06
 * @Description:
 */
public class GwFrame {

    private static GwFrame INSTANCE;

    private GwFrame() {
    }

    public static GwFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = GwFrameBuilder.GW_FRAME;
        }
        return INSTANCE;
    }

    /**
     * 用于权限提示对话框
     */
    public GwFrame setAppName(String name) {
        AppConfig.AppName = name;
        return INSTANCE;
    }

    public GwFrame setAdaptWidth(int adaptWidth) {
        AppConfig.AdaptWidth = adaptWidth;
        return INSTANCE;
    }

    public GwFrame isDebug(boolean isDebug) {
        AppConfig.DEBUG = isDebug;
        initToast();
        initHttp();
        return INSTANCE;
    }

    public GwFrame setBaseUrl(String url) {
        HttpUtil.init();
        BaseUrl.saveBaseUrl(url);
        return INSTANCE;
    }

    public GwFrame setDownload(String url) {
        BaseUrl.DOWNLOAD_APK = url;
        return INSTANCE;
    }

    public GwFrame setFtp(String host, int port, String userName, String pwd) {
        FTPConfig.HOST = host;
        FTPConfig.PORT = port;
        FTPConfig.USERNAME = userName;
        FTPConfig.PASSWORD = pwd;
        return INSTANCE;
    }

    private void initToast() {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.setBgColor(Color.parseColor("#AA000000"));
        ToastUtils.setMsgColor(Color.WHITE);
    }

    private void initHttp() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        RxHttp.init(client, AppConfig.DEBUG);
    }

    private static class GwFrameBuilder {
        private static final GwFrame GW_FRAME = new GwFrame();
    }
}
