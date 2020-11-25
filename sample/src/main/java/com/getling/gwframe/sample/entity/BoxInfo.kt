package com.getling.gwframe.sample.entity

import com.getling.gwframe.data.entity.BaseInfoEntity

/**
 * @Author: getling
 * @CreateDate: 2020/11/18 14:51
 * @Description:
 */
class BoxInfo : BaseInfoEntity() {
    var pileNo: String? = null//"J331V2011140010P0001",
    var boxID: String? = null//"J331V20111400100001",
    var supCode: String? = null//"AABPS",
    var supName: String? = null//"天津东方兴泰工业科技股份有限公司",
    var materialID: String? = null//"2300SZ080001A",
    var materialName: String? = null//"前副车架分装模块",
    var unit: String? = null//null,
    var batch: String? = null//"20201114",
    var fullNum: Double? = null//100,
    var planinNum: Double? = null//100, 送货数量
    var deliverNum: Double? = null//100,收货数量
    var destroyNum: String? = null//null,
    var stockinNum: String? = null//null,
    var displaceNum: String? = null//null,
    var sjdhtime: String? = null//null,
    var fydbh: String? = null//"J331V2011140010",
}