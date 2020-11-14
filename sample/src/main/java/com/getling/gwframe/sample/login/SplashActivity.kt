package com.getling.gwframe.sample.login

import android.content.Intent
import android.os.Handler
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.StringUtils
import com.getling.gwframe.constant.SPConstant
import com.getling.gwframe.login.BaseSplashActivity

class SplashActivity : BaseSplashActivity() {
    override fun initData() {
        Handler().postDelayed({
            if (StringUtils.isEmpty(SPUtils.getInstance().getString(SPConstant.SP_USER_ID))) {
                startActivity(Intent(context, ManageActivity::class.java))
            } else {
                startActivity(Intent(context, ManageActivity::class.java))
            }
            finish()
        }, 2000)
    }
}