package com.getling.gwframe.ftp;

import com.getling.gwframe.config.AppConfig;

import java.io.File;

/**
 * @Author: getling
 * @CreateDate: 2019/6/27 11:54
 * @Description: FTP服务器参数
 */
public class FTPConfig {

    public static String HOST;
    public static int PORT;
    public static String USERNAME;
    public static String PASSWORD;

    private final static String HOST_TEST = "";
    private final static String HOST_PRO = "";

    public final static String UPLOAD_DIRECTORY_ZY = File.separator + AppConfig.AppName + File.separator;
}
