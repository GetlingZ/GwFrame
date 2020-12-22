package com.getling.gwframe.sample.ui.qrcode

import com.blankj.utilcode.util.LogUtils
import com.getling.gwframe.app.BaseActivity
import com.getling.gwframe.sample.R
import com.getling.gwframe.sample.databinding.ActivityQrCodeBinding
import com.getling.gwframe.scan.ScanUtil
import com.getling.gwframe.scan.ScanView

class QrCodeActivity : BaseActivity<ActivityQrCodeBinding>() {
    override fun bindLayout(): Int {
        return R.layout.activity_qr_code
    }

    override fun initParam() {
        ScanUtil.init()
    }

    override fun initView() {
        setMyTitle("扫描")

        mDataBinding.scanCode.setOnScanListener(object : ScanView.OnScanListener {
            override fun onScan(data: String) {
                LogUtils.e(data)
            }
        })
    }

    override fun initData() {
    }
}