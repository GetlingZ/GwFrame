package com.getling.gwframe.sample

import android.os.Handler
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.SmartSwipeWrapper
import com.billy.android.swipe.SwipeConsumer
import com.billy.android.swipe.consumer.DrawerConsumer
import com.billy.android.swipe.listener.SimpleSwipeListener
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
        setMyTitleSize(22F)

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
        adapter.data = listOf("", "", "")
        mDataBinding.rvMain.adapter = adapter

        val swipeView = layoutInflater.inflate(R.layout.view_swipe, null, false)

        val listener: SimpleSwipeListener = object : SimpleSwipeListener() {
            override fun onSwipeOpened(
                wrapper: SmartSwipeWrapper,
                consumer: SwipeConsumer,
                direction: Int
            ) {
                super.onSwipeOpened(wrapper, consumer, direction)
                LogUtils.e(direction)
            }

            override fun onSwipeClosed(
                wrapper: SmartSwipeWrapper,
                consumer: SwipeConsumer,
                direction: Int
            ) {
                super.onSwipeClosed(wrapper, consumer, direction)
                LogUtils.e(direction)
            }
        }

        val drawerConsumer = DrawerConsumer() //horizontal menu
            .setHorizontalDrawerView(swipeView) //top menu
            .setTopDrawerView(swipeView) //bottom menu
            .setBottomDrawerView(swipeView) //set the translucent color of scrim (default is 0:transparent)
            .setScrimColor(0x7F000000) //set the shadow color follow the drawer while swiping (default is 0:transparent)
            .setShadowColor(-0x80000000)
            .setShadowSize(SmartSwipe.dp2px(10, this))
            .addListener(listener) //set edge size to swipe to 20dp (default is 0: whole range of the contentView bounds)
            .setEdgeSize(SmartSwipe.dp2px(20, this))
            .lockAllDirections()
            .`as`(DrawerConsumer::class.java)

        SmartSwipe.wrap(this)
            .addConsumer(drawerConsumer)

        Handler().postDelayed({
            drawerConsumer.smoothRightOpen()
        }, 1000)
    }

    override fun initData() {
    }

}
