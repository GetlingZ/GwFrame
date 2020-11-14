package com.getling.gwframe.login

import android.view.animation.AlphaAnimation
import android.widget.TextView
import com.getling.gwframe.R
import com.getling.gwframe.app.BaseActivity
import com.getling.gwframe.databinding.ActivitySplashBaseBinding

open class BaseSplashActivity : BaseActivity<ActivitySplashBaseBinding>() {

    protected lateinit var tvTitle: TextView

    override fun bindLayout(): Int {
        return R.layout.activity_splash_base
    }

    override fun initParam() {
    }

    override fun initView() {
        tvTitle = findViewById(R.id.tv_title_splash)

        val animation = AlphaAnimation(0f, 1f)
        animation.duration = 1000

        tvTitle.animation = animation
    }

    override fun initData() {
    }

}
