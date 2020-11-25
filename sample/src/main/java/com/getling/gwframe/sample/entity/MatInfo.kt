package com.getling.gwframe.sample.entity

import com.getling.gwframe.data.entity.BaseInfoEntity

/**
 * @Author: getling
 * @CreateDate: 2020/11/18 14:51
 * @Description:
 */
class MatInfo : BaseInfoEntity() {
    var deliveryCode: String? = null//"J331V2011140010",
    var itemNum: String? = null//51,
    var materialID: String? = null//"2300SZ080001A",
    var materialName: String? = null//"前副车架分装模块",
    var unit: String? = null//"EA",
    var planinNum: Double? = null//1035,
    var deliverNum: Double? = null//1035,
    var batch: String? = null//"20201114",
    var agreementCode: String? = null//"4301066898",
    var agreementType: String? = null//"ZL01",
    var batchTotal: String? = null//0,
    var iNewbatch: String? = null//1,
    var iExterior: String? = null//0,
    var checkType: String? = null//"正常",
}