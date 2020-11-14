package com.getling.gwframe.sample.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.getling.gwframe.login.BaseSplashActivity
import com.getling.gwframe.sample.R

class SplashActivity : BaseSplashActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}