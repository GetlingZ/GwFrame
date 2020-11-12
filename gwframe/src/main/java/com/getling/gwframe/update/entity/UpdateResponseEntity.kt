package com.getling.gwframe.update.entity

import com.getling.gwframe.data.entity.BaseEntity
import com.getling.gwframe.data.entity.BaseInfoEntity

/**
 * @Author: getling
 * @CreateDate: 2019/6/27 14:48
 * @Description:
 */
class UpdateResponseEntity : BaseEntity {
    var entity: FactoryInfo? = null

    class FactoryInfo : BaseInfoEntity() {
        var factorycode: String? = null// "B410",
        var fullName: String? = null// "曼德光电",
        var shortName: String? = null// "曼德光电",
        var address: String? = null// "1",
        var postNum: String? = null// "1",
        var linkmen: String? = null// "3",
        var connectway: String? = null// "1",
        var fax: String? = null// "1",
        var img: String? = null// null,
        var tekephone: String? = null// null,
        var enablejis: String? = null// 0,
        var jisfix: String? = null// null,
        var supcode: String? = null// "CAGTX",
        var supname: String? = null// "曼德电子电器有限公司保定光电分公司",
        var batchwithclass: String? = null// 0,
        var waterlength: String? = null// 0,
        var keypartcoderule: String? = null// 0,
        var mrsource: String? = null// 0,
        var savedischargeinorder: String? = null// 0,
        var supstockinway: String? = null// 1,
        var inspectcode: String? = null// "1",
        var returncode: String? = null// "1.0.1.94",
        var suppliercode: String? = null// "AAAZW",
        var wlCentercode: String? = null// "1",
        var isserviceopen: String? = null// 0,
        var editImg: String? = null// null,
        var auditImg: String? = null// null,
        var mainpoorstock: String? = null// "BLP",
        var allowNegative: String? = null// 1,
        var allowDeleteNew: String? = null// 1,
        var allowAddNew: String? = null// 1,
        var moveRightnow: String? = null// 1,
        var replaceMatStock: String? = null// "123",
        var hierarchy: String? = null// "JGDP/MSe-SP09-01-c03",
        var beat: String? = null// null,
        var stockInHierarchy: String? = null// "1",
        var logconroler: String? = null// 0,
        var batchnum: String? = null// 0,
        var printsummary: String? = null// 0,
        var isqueryaccountperiod: String? = null// 0,
        var autoeditmatdisresult: String? = null// 0,
        var autosavesfpullbom: String? = null// 0,
        var isstockwarning: String? = null// 0,
        var autoreplace: String? = null// 0,
        var pienable: String? = null// 0,
        var iserpprod: String? = null// 1,
        var isfactory: String? = null// 1,
        var isquality: String? = null// 0,
        var isWMSPull: String? = null// 0,
        var ifcheckqy: String? = null// 0,
        var isBARESTOCKIN: String? = null// 0,
        var isCONTAINERSTOCKIN: String? = null// 1,
        var isMIDDLECHECK: String? = null// 1,
        var isOFFLINECHECK: String? = null// 1,
        var isSUPPLIERSEND: String? = null// 0,
        var isUPLOADSAP: String? = null// 0,
        var sstorage: String? = null// 1,
        var stockcode: String? = null// "1",
        var isPurchaseStockInCheck: String? = null// 1,
        var isPQCStockInCheck: String? = null// 1,
        var isLeaveSendCheck: String? = null// 1,
        var appVersion: String? = null// "1.0.0"
    }
}