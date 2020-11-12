package com.getling.gwframe.config

import com.blankj.utilcode.util.PathUtils
import com.getling.gwframe.app.GwFrame
import java.io.File

/**
 * @Author: getling
 * @CreateDate: 2020/2/10 16:13
 * @Description:
 */
object PathConfig {

    /**
     * lms项目
     * LMS/app name/Picture
     * LMS/app name/Download
     */
    private val RootName = GwFrame.getInstance().factory.rootName
    private val Path = RootName + File.separator + GwFrame.getInstance().factory.appName
    private const val Picture = "Picture"
    private const val Download = "Download"

    val PicturePath =
        PathUtils.getExternalStoragePath() + File.separator + Path + File.separator + Picture + File.separator
    val DownloadPath =
        PathUtils.getExternalStoragePath() + File.separator + Path + File.separator + Download + File.separator

}