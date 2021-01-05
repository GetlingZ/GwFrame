package com.getling.gwframe.sample.ui.key

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.getling.gwframe.sample.R
import com.getling.gwframe.sample.ui.broadcast.HomeReceiver


class KeyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key)
        startLockTask()

        //注册广播
        val innerReceiver = HomeReceiver()
        val intentFilter = IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        registerReceiver(innerReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        //重写recent键方法
        for (j in 0..49) {
            val activityManager = applicationContext
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityManager.moveTaskToFront(taskId, 0)
        }
    }

    /**
     * 重写onKeyDown方法可以拦截系统默认的处理
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        LogUtils.e(keyCode)
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                Toast.makeText(this, "后退键", Toast.LENGTH_SHORT).show()
                return true
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                Toast.makeText(this, "声音+", Toast.LENGTH_SHORT).show()
                return false
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                Toast.makeText(this, "声音-", Toast.LENGTH_SHORT).show()
                return false
            }
            KeyEvent.KEYCODE_VOLUME_MUTE -> {
                Toast.makeText(this, "静音", Toast.LENGTH_SHORT).show()
                return false
            }
            KeyEvent.KEYCODE_HOME -> {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                return true
            }
            KeyEvent.KEYCODE_MENU -> {
                Toast.makeText(this, "菜单", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onKeyDown(keyCode, event)
        }
    }

    override fun onAttachedToWindow() {
        //关键：在onAttachedToWindow中设置FLAG_HOMEKEY_DISPATCHED
        super.onAttachedToWindow()
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        // 返回true，不响应其他key
        return true
    }
}