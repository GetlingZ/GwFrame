package com.getling.gwframe.ftp;

import com.getling.gwframe.app.GwFrame;

import java.io.File;

/**
 * @Author: getling
 * @CreateDate: 2019/6/27 11:54
 * @Description: FTP服务器参数
 */
public class FTPConfig {

    public static String HOST = GwFrame.getInstance().factory.getFtpHost();
    public static int PORT = GwFrame.getInstance().factory.getFtpPort();
    public static String USERNAME = GwFrame.getInstance().factory.getFtpUserName();
    public static String PASSWORD = GwFrame.getInstance().factory.getFtpPassword();

    public final static String UPLOAD_DIRECTORY_ZY =
            File.separator + GwFrame.getInstance().factory.getAppName() + File.separator;
}
