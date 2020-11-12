package com.getling.gwframe.config;

/**
 * @Author: getling
 * @CreateDate: 2020/11/4 10:46
 * @Description: app配置工厂
 */
public interface AppConfigFactory {
    /**
     * 本地sd卡，顶级目录
     */
    String getRootName();

    /**
     * 项目名称，用于权限验证弹框
     */
    String getAppName();

    String getBaseUrl();

    /**
     * ftp相关
     */
    String getFtpHost();

    int getFtpPort();

    String getFtpUserName();

    String getFtpPassword();

    boolean isDebug();

    /**
     * 屏幕适配宽度
     */
    int getAdaptWidth();

    /**
     * 获取服务器上APP版本号
     */
    String getUpdateInfoUrl();

    /**
     * apk下载地址
     */
    String getDownloadApkUrl();
}
