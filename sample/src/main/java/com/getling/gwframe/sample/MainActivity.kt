package com.getling.gwframe.sample

import com.blankj.utilcode.util.LogUtils
import com.getling.gwframe.app.BaseActivity
import com.getling.gwframe.bus.BusUtil
import com.getling.gwframe.bus.event.PermissionEvent
import com.getling.gwframe.bus.rxbus.RxBus
import com.getling.gwframe.rv.decoration.HorizontalDecoration
import com.getling.gwframe.sample.adapter.MainAdapter
import com.getling.gwframe.sample.databinding.ActivityMainBinding
import rxhttp.RxHttp

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun initParam() {
    }

    override fun initView() {
        setMyTitle("进货质检")

        mDataBinding.name = "name"

        BusUtil.subscribe(this, object : RxBus.BusCallback<PermissionEvent>() {
            override fun onEvent(t: PermissionEvent?) {
                LogUtils.e("1111")
            }
        })

        BusUtil.postEvent(PermissionEvent())

        RxHttp.get("res")
            .add("", "")
            .asString()
            .subscribe()

//        startActivity(Intent(this, ScanQRCodeActivity::class.java))
        setRootBackground(R.drawable.main_bg)

        val dec = HorizontalDecoration(1)
        dec.setDrawLast(true)
        mDataBinding.rvMain.addItemDecoration(dec)
        val adapter = MainAdapter()
        adapter.data = listOf("", "","")
        mDataBinding.rvMain.adapter = adapter
    }

    override fun initData() {
    }

}
