package com.getling.gwframe.sample.factory

import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.StringUtils
import com.getling.gwframe.config.AppConfigFactory
import com.getling.gwframe.sample.R

/**
 * @Author: getling
 * @CreateDate: 2020/11/4 13:59
 * @Description:
 */
class TestFactory : AppConfigFactory {

    override fun getRootName(): String {
        return "LMS"
    }

    override fun getAppName(): String {
        return StringUtils.getString(R.string.app_name)
    }

    override fun getBaseUrl(): String {
        return ""
    }

    override fun getFtpHost(): String {
        return "10.255.30.7"
    }

    override fun getFtpPort(): Int {
        return 9002
    }

    override fun getFtpUserName(): String {
        return "ftpuser"
    }

    override fun getFtpPassword(): String {
        return "123@123.com"
    }

    override fun isDebug(): Boolean {
        return true
    }

    override fun getAdaptWidth(): Int {
        return 720
    }

    override fun getUpdateInfoUrl(): String {
        return "/FactoryInformationSet/selectFactorySetByCode"
    }

    override fun getDownloadApkUrl(): String {
        return "http://10.255.30.7:9001/lms-light-qc.apk"
    }

    override fun getFileProviderPath(): String {
        return AppUtils.getAppPackageName() + ".fileprovider"
    }
}