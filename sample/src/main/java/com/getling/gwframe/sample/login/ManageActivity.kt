package com.getling.gwframe.sample.login

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.getling.gwframe.login.BaseManageActivity

class ManageActivity : BaseManageActivity() {
    override fun initView() {
        super.initView()

  LogUtils.e(ScreenUtils.getAppScreenWidth())
    }
}