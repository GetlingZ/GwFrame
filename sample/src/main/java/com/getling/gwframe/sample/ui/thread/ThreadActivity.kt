package com.getling.gwframe.sample.ui.thread

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.LogUtils
import com.getling.gwframe.sample.R
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ThreadActivity : AppCompatActivity() {

    private var isStart = false
    private lateinit var btnChange: Button
    private var disposable:Disposable?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)
        initView()
        initThread()
    }

    private fun initView() {
        btnChange = findViewById(R.id.btn_change)
        btnChange.setOnClickListener {
            isStart = !isStart
            LogUtils.e(isStart)
        }
    }

    private fun initThread() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable?) {
                    disposable = d
                }

                override fun onNext(t: Long?) {
                    LogUtils.e(isStart)
                }

                override fun onError(e: Throwable?) {
                }

                override fun onComplete() {
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}