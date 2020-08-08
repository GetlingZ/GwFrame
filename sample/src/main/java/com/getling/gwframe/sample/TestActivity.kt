package com.getling.gwframe.sample

import com.getling.gwframe.app.BaseActivity
import com.getling.gwframe.sample.databinding.ActivityTestBinding

class TestActivity : BaseActivity<ActivityTestBinding>() {
    override fun bindLayout(): Int {
        return R.layout.activity_test
    }

    override fun initParam() {
    }

    override fun initView() {
        setMyTitle(R.string.app_name)
        setRootBackground(R.drawable.main_bg)
    }

    override fun initData() {
    }
}
