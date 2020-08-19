package com.gw.lms.decorate.data.source.remote

/**
 * @Author: getling
 * @CreateDate: 2019/11/12 15:46
 * @Description: 接口路径
 */
object ServerUrl {

    /**
     * 登录
     */
    const val LOGIN = "/rest/sys/login/username/password/1/1/1/1/1"

    /**
     * 获取工厂列表
     */
    const val POST_FACTORY_LIST = "factoryCenter/getFactory"

    /**
     * 检查版本更新
     */
    const val GET_UPDATE = "rest/factoryInformationSet/query/"

    /*----------------------库存调整-----------------------*/

    /**
     * 根据物料编码和仓库编码查询物料库存
     */
    const val POST_CHECK_STOCK_CHANGE_LIST = "rest/stockAdjust/selectProductStock"
    /**
     * 库存调整保存
     */
    const val POST_SAVE_STOCK_CHANGE = "rest/stockAdjust/productStockChange"

    /**
     * 库存调整，扫码工装码
     */
    const val POST_SCAN_TOOL_CODE_SC = "rest/StockTJAdjust/queryByContainerCodePDA"

    /**
     * 库存调整，保存
     */
    const val POST_SAVE_SC_CHANGE = "rest/StockTJAdjust/stockTJAdjustPDA"
    /**
     * 库存调整，多工装保存
     */
    const val POST_SAVE_SC_CHANGE_MULTI = "rest/StockTJAdjust/stockTJAdjustDoublePDA"

    /*-------------------------线体采集-------------------------------*/

    /**
     * 扫描二维码
     * ipackage：1座椅条形码采集扫描 0其他；
     */
    const val POST_SCAN_CODE = "rest/qrCode/selectScanByLine?ipackage="

    /**
     * 保存
     */
    const val POST_SAVE_PIPE_COLLECT = "rest/qrCode/saveScanByLine"

    /*--------------------------下线入库-----------------------------*/

    /**
     * 保存
     */
    const val POST_SAVE_OFF_LINE_IN = "rest/qrCode/saveScanByStockIn?iType="

    /*--------------------------大枪下线采集-----------------------------*/

    /**
     * 保存
     */
    const val POST_SAVE_GUN_OFF_LINE = "rest/qrCode/saveScanByStockInDQ"

    /*--------------------------座椅采集条码-----------------------------*/

    /**
     * 保存
     */
    const val POST_SAVE_SEAT_COLLECT_BAR_CODE = "rest/qrCode/saveScanByStockInZY?iType="

    /*--------------------------座椅其他入库-----------------------------*/

    /**
     * 获取入库类型列表
     */
    const val GET_STOCK_IN_TYPE = "rest/configuredata/sysBaseInfo/getDictItem/"

    /*--------------------------座椅其他出库-----------------------------*/

    /**
     * 扫描条形码
     */
    const val POST_SCAN_SEAT_OTHER_OUT = "rest/productStockIn/selectStockByScan?ipackage="

    /**
     * 座椅其他出库，保存
     */
    const val POST_SAVE_SEAT_O_O = "rest/productStockIn/productInOutBysCodeSave"

    /*-------------------------------退库管理--------------------------------*/

    /**
     * 退库管理，扫描二维码
     */
    const val POST_SCAN_STOCK_RETREAT = "rest/qrCode/selectMatByQrCode"

    /**
     * 退库管理，保存
     */
    const val POST_SAVE_STOCK_RETREAT = "rest/productStockIn/productInOutSave"

    /*-------------------------------零星出库--------------------------------*/
    /**
     * 零星出库，扫描二维码
     */
    const val POST_SCAN_E_OUT_PIECE = "rest/productStockIn/selectStockByQrCode"

    /**
     * 零星出库，保存
     */
    const val POST_SAVE_E_OUT_PIECE = "rest/productStockIn/productInOutSaveQT"

    /*-------------------------------座椅出库发运--------------------------------*/

    /**
     * 获取物料工厂
     */
    const val POST_MAT_FACTORY_INFO = "rest/transitPlan/selectFactoryList"

    /**
     * 根据日期查询车次
     */
    const val POST_CAR_BATCH = "rest/transitPlan/selectCarTimeList"

    /**
     * 根据日期查询车次，改版后
     */
    const val POST_CAR_BATCH_CHANGE = "rest/transitServer/selectCarTimeList"

    /**
     * 根据日期，车次筛选数据
     */
    const val POST_SEARCH_DATA_SOD = "rest/transitPlanDetail/selectPrepareList"
    /**
     * 改版
     */
    const val POST_SEARCH_DATA_SOD_CHANGE = "rest/transitServer/selectPrepareList"

    /**
     * 座椅出库发运，保存
     */
    const val POST_SAVE_SOD = "rest/productStockIn/productOutByTransitSave"

    /*-------------------------------主机厂E看板备料出库--------------------------------*/

    /**
     * 扫描E看板
     */
    const val POST_SCAN_E_CODE = "rest/transitPlan/selectEboardScan"

    /**
     * 扫描E看板，改版
     */
    const val POST_SCAN_E_CODE_CHANGE = "rest/transitServer/selectEboardScan"

    /**
     * 扫描条码
     */
    const val POST_SCAN_BAR_CODE = "rest/qrCode/selectByBarCode?barCode="

    /**
     * 扫描条码，改版
     */
    const val POST_SCAN_BAR_CODE_CHANGE = "rest/transitServer/selectQRMaterial"

    /**
     * 出库
     */
    const val POST_SAVE_FED = "rest/transitPlan/sendEboardPre"

    /**
     * 出库，改版
     */
    const val POST_SAVE_FED_CHANGE = "rest/transitServer/sendEboardPre"

    /*-------------------------------备料出库--------------------------------*/

    /**
     * 备料出库详情，扫描二维码
     */
    const val POST_SCAN_POD = "rest/qrCode/selectForPreScan?qrCode="

    /**
     * 备料出库，扫描二维码，改版
     */
    const val POST_SCAN_POD_CHANGE = "rest/transitServer/selectQRMaterial/"

    /**
     * 保存出库
     */
    const val POST_SAVE_POD = "rest/transitPlanDetail/prepare"

    /**
     * 保存出库，改版
     */
    const val POST_SAVE_POD_CHANGE = "rest/transitServer/prepare"

    /**
     * 检验扫描的物料是否按照顺序
     */
    const val POST_CHECK_DATA_POD = "rest/transitServer/checkOutSequence"

    /*-------------------------------顺建计划校验--------------------------------*/
    /**
     * 顺建计划校验，扫描二维码
     */
    const val POST_SCAN_QR_PC = "rest/qrCode/selectByQrCodeSJ"
    /**
     * 扫描条码
     */
    const val POST_SCAN_BAR_PC = "rest/qrCode/selectByBarcodeSJ"

    /**
     * 保存
     */
    const val POST_SAVE_PC = "rest/qrCode/saveByQrCodeSJ?id="

    /*-------------------------------发运--------------------------------*/
    /**
     * 发运，保存
     */
    const val POST_SAVE_DIS = "rest/transitPlanDetail/send"

    /**
     * 发运，保存，改版
     */
    const val POST_SAVE_DIS_CHANGE = "rest/transitServer/send"

    /**
     * 扫描
     */
    const val POST_SCAN_DIS = "rest/qrCode/selectForSendScan?qrCode="

    /**
     * 发运扫描，改版
     */
    const val POST_SCAN_DIS_CHANGE = "rest/transitServer/selectDeliveryQRMaterial"

    /*-------------------------------泡棉退库--------------------------------*/
    /**
     * 扫描条码
     */
    const val POST_SCAN_BAR_CODE_CR = "rest/stockAdjust/selectStockByBarCode"

    /*-------------------------------头枕入库--------------------------------*/
    /**
     * 头枕入库扫描二维码
     */
    const val POST_SCAN_CODE_HPI = "rest/qrCode/selectStockInByQrCodeSJ"
    /**
     * 头枕入库，直接入库
     */
    const val POST_SAVE_IN_HPI = "rest/qrCode/saveStockInByQrCodeSJ?id="
    /**
     * 顺建校验入库 保存
     */
    const val POST_SAVE_HPI = "rest/qrCode/tempSaveStockInByQrCodeSJ?id="

    /*-------------------------------头枕套下线录入--------------------------------*/
    /**
     * 根据物料编码查询bom版本
     */
    const val POST_SEARCH_MAT_BOM = "rest/productStockIn/selectBomVerBysCode"

    /**
     * 保存
     */
    const val POST_SAVE_HPOI = "rest/productStockIn/productStockInSaveBysCode"

    /*-------------------------------查询库区--------------------------------*/
    /**
     * 根据仓库编码查询库区
     */
    const val GET_AREA_BY_CODE = "rest/factory/storageorgunit/queryAreaByStorageCode?storageCode="

    /**
     * 根据仓库ID查询库区
     */
    const val GET_AREA_BY_ID = "rest/factory/storageorgunit/queryByFactoryId/"

    /*-------------------------------条码退库--------------------------------*/
    /**
     * 条码退库，保存
     */
    const val POST_SAVE_BAR_CODE_RETREAT = "rest/qrCode/saveScanByStockInTM"

    /*-------------------------------发运收货--------------------------------*/
    /**
     * 扫描，发运单
     */
    const val POST_SCAN_DIS_CODE_DR = "rest/productStockIn/queryTransitDetail"

    /**
     * 保存
     */
    const val POST_SAVE_DR = "rest/productStockIn/saveTransitDetail"
}