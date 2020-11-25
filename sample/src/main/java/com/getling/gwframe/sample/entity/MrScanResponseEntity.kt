package com.getling.gwframe.sample.entity

import com.getling.gwframe.data.entity.BaseInfoEntity

/**
 * @Author: getling
 * @CreateDate: 2020/11/16 17:20
 * @Description: 原材料收货扫描
 */
class MrScanResponseEntity : BaseInfoEntity() {
    /**
     * 物料
     */
    var detailList: List<MatInfo>? = null

    /**
     * 箱明细
     */
    var boxList: List<BoxInfo>? = null

    /**
     * 发运单明细
     */
    var factory: String? = null//"J331",
    var deliveryCode: String? = null//"J331V2011140010",
    var orderCode: String? = null//"J331I2011140008",
    var stockInWay: String? = null//4,
    var planDate: String? = null//null,
    var deliveryDate: String? = null//null,
    var supCode: String? = null//"AABPS",
    var supName: String? = null//"天津东方兴泰工业科技股份有限公司",
    var logCode: String? = null//"AABPS",
    var logName: String? = null//"天津东方兴泰工业科技股份有限公司",
    var submissionType: String? = null//0,
    var remark: String? = null//null,
    var releaseUser: String? = null//null,
    var releaseTime: String? = null//"2020-11-14",
    var state: String? = null//null,
    var instate: String? = null//1,
    var storage: String? = null//"CS001",
    var inReason: String? = null//null,
    var inremark: String? = null//null,
    var tplArea: String? = null//null,
    var materialID: String? = null//null,
    var iPress: String? = null//null,
}