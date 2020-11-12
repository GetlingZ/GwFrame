package com.getling.gwframe.app;

import android.graphics.Color;
import android.view.Gravity;

import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.getling.gwframe.config.AppConfigFactory;
import com.getling.gwframe.http.BaseUrl;
import com.getling.gwframe.http.utils.HttpUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import rxhttp.RxHttp;
import rxhttp.RxHttpPlugins;

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

    public AppConfigFactory factory;

    public GwFrame setAppConfig(AppConfigFactory factory) {
        this.factory = factory;
        initToast();
        initHttp();
        return INSTANCE;
    }

    public GwFrame setBaseUrl(String url) {
        HttpUtil.init();
        BaseUrl.saveBaseUrl(url);
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
        RxHttp.init(client, factory.isDebug());

        //设置缓存目录为：Android/data/{app包名目录}/cache/RxHttpCache
        File cacheDir = new File(PathUtils.getExternalAppCachePath(), "RxHttpCache");
        RxHttpPlugins.setCache(cacheDir, 10 * 1024 * 1024);
    }

    private static class GwFrameBuilder {
        private static final GwFrame GW_FRAME = new GwFrame();
    }
}
