package com.getling.gwframe.scan

/**
 * @Author: getling
 * @CreateDate: 2019/12/9 16:11
 * @Description:
 */
object ScanConfig {
    /**
     * 扫描方式
     * 0    honeywell
     * 2    almex
     * 3    普通PDA
     * 4    手机
     */
    const val SCAN_MODE_HONEYWELL = 0
    const val SCAN_MODE_ALMEX = 1
    const val SCAN_MODE_PDA = 2
    const val SCAN_MODE_PHONE = 3

    var SCAN_MODE = SCAN_MODE_HONEYWELL

    const val Honeywell = "honeywell"
    const val ALMEX = "almex"
    const val SCAN_ACTION_ALMEX = "com.android.server.scannerservice.broadcast"
    const val SCAN_NAME_ALMEX = "almex"

    val Rom = setOf(
        "honor",
        "huawei",
        "xiaomi",
        "meizu",
        "vivo",
        "oppo",
        "samsung",
        "realme",
        "oneplus"
    )
}