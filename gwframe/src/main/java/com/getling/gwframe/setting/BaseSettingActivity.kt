package com.getling.gwframe.setting

import android.util.SparseArray
import com.blankj.utilcode.util.SPUtils
import com.getling.gwframe.R
import com.getling.gwframe.app.BaseActivity
import com.getling.gwframe.constant.SPConstant
import com.getling.gwframe.databinding.ActivitySettingBaseBinding
import com.getling.gwframe.rv.decoration.HoriPaddingDecoration
import com.getling.gwframe.scan.ScanConfig
import com.getling.gwframe.scan.ScanUtil
import com.getling.gwframe.setting.adapter.SettingAdapter
import com.getling.gwframe.update.UpdateUtil
import com.getling.gwframe.utils.DialogUtil

/**
 * @CreateDate: 2019/12/3 8:51
 * @Author: getling
 * @Description: 设置页
 */
open class BaseSettingActivity : BaseActivity<ActivitySettingBaseBinding>() {

    lateinit var adapter: SettingAdapter
    val contentArray = SparseArray<String>()

    override fun bindLayout(): Int {
        return R.layout.activity_setting_base
    }

    override fun initParam() {
    }

    override fun initView() {
        setMyTitle(R.string.title_setting)

        adapter = SettingAdapter()
        mDataBinding.rvSetting.adapter = adapter
        mDataBinding.rvSetting.addItemDecoration(HoriPaddingDecoration())
    }

    override fun initData() {

        contentArray.put(0, resources.getStringArray(R.array.ScanMode)[ScanConfig.SCAN_MODE])
        adapter.data = resources.getStringArray(R.array.setting_item_list).toMutableList()
        adapter.setContentArray(contentArray)

        adapter.setOnItemClickListener { v, position ->
            when (position) {
                0 ->
                    //扫描方式
                    changeScanMode()
                1 ->
                    //检查更新
                    checkUpDate()
                2 ->
                    //退出登录
                    logoutDialog()
                else -> {
                }
            }
        }
    }

    open fun changeScanMode() {
        DialogUtil.showSingleChoiceDialog(
            context,
            "选择方式",
            resources.getStringArray(R.array.ScanMode), ScanConfig.SCAN_MODE
        ) { _, which ->
            changeScanMode(which)
        }
    }

    open fun changeScanMode(which: Int) {
        SPUtils.getInstance().put(SPConstant.SP_SCAN_MODE, which)
        ScanConfig.SCAN_MODE = which
        if (ScanConfig.SCAN_MODE == ScanConfig.SCAN_MODE_PHONE) {
            ScanUtil.onDestroy()
        } else {
            ScanUtil.init()
        }
        adapter.getContentArray()
            ?.put(0, resources.getStringArray(R.array.ScanMode)[ScanConfig.SCAN_MODE])
        adapter.notifyItemChanged(0)
    }

    open fun logoutDialog() {
        DialogUtil.showNormalDialog(
            context, "", "退出登录？", "确认"
        ) { dialog, which ->
            logout()
        }
    }

    open fun logout() {

    }

    open fun checkUpDate() {
        UpdateUtil.checkUpdate(this, true)
    }
}
